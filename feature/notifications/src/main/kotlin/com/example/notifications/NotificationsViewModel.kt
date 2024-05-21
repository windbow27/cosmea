package com.example.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.service.UserService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(private var userService: UserService, private val userId: String): ViewModel() {
    private val _notifications = MutableStateFlow(listOf<Notification>())
    private val _friendRequests = MutableStateFlow(listOf<Pair<String, String>>())
    private val friendRequests: StateFlow<List<Pair<String, String>>> get() = _friendRequests
    val notifications: StateFlow<List<Notification>> get() = _notifications

    init {
        fetchFriends()
    }

    private fun fetchFriends() {
        viewModelScope.launch {
            val friends = userService.getFriendRequests(userId).map { friendId ->
                async {
                    val friendUsername = userService.getUsernameById(friendId)
                    friendId to (friendUsername ?: "")
                }
            }.awaitAll()
            Log.d("NotificationsViewModel", "fetchFriends: $friends")
            _friendRequests.value = friends
            createNotifications()
        }
    }

    private fun createNotifications() {
        val notifications = friendRequests.value.map { (friendId, friendUsername) ->
            Notification(friendId, friendUsername, "sent you a friend request")
        }
        _notifications.value = notifications
    }
}

class NotificationViewModelFactory(private val userService: UserService, private val userId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationsViewModel(userService, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}