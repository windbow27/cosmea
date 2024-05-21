package com.example.messages

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.service.ChannelService
import com.example.data.service.UserService
import com.example.model.DirectMessage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class MessagesViewModel (
    private var userService: UserService,
    private var channelService: ChannelService,
    private val userId: String
): ViewModel() {
    private val _directMessages = MutableStateFlow(listOf<DirectMessage>())
    private val _friends = MutableStateFlow(listOf<Pair<String, String>>())

    val directMessages: StateFlow<List<DirectMessage>> get() = _directMessages
    private val friends: StateFlow<List<Pair<String, String>>> get() = _friends

    init {
        fetchFriends()
        observeDirectMessages()
    }

    private fun fetchFriends() {
        viewModelScope.launch {
            val friends = userService.getFriends(userId).map { friendId ->
                async {
                    val friendUsername = userService.getUsernameById(friendId)
                    friendId to (friendUsername ?: "")
                }
            }.awaitAll()
            Log.d("MessagesViewModel", "fetchFriends: $friends")
            _friends.value = friends
            createDirectMessages()
        }
    }

    private fun observeDirectMessages() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(userId)

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
                val friends = snapshot.get("friends") as? MutableList<String> ?: mutableListOf()
                _friends.value = friends.map { it to "" }
                createDirectMessages()
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    private fun createDirectMessages() {
        val directMessages = mutableListOf<DirectMessage>()
        val jobs = friends.value.map { (friendId, friendUsername) ->
            viewModelScope.launch {
                val id = async { channelService.getDirectMessageId(userId, friendId) }.await()
                val lastMessage = async { channelService.getLastMessage(id) }.await()
                val directMessage = DirectMessage(
                    id = id,
                    friendId = friendId,
                    friendUsername = friendUsername,
                    lastMessage = lastMessage,
                )
                directMessages.add(directMessage)
            }
        }
        viewModelScope.launch {
            jobs.joinAll()
            Log.d("MessagesViewModel", "createDirectMessages: $directMessages")
            _directMessages.value = directMessages
        }
    }
}

class MessagesViewModelFactory(private val userService: UserService, private val channelService: ChannelService, private val userId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessagesViewModel(userService, channelService, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}