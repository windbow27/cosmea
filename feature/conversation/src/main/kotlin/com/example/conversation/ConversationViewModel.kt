package com.example.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.service.ChannelService
import com.example.data.service.MessageService
import com.example.data.service.UserService
import com.example.model.ChannelData
import com.example.model.MessageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ConversationViewModel(private val channelService: ChannelService, private val messageService: MessageService, private val userService: UserService, channelId: String) : ViewModel() {
    private val _channelData = MutableStateFlow(ChannelData("","", "", mutableListOf(), mutableListOf()))
    private val _messageData = MutableStateFlow(listOf<MessageData>())
    val channelData: StateFlow<ChannelData> get() = _channelData
    val messageData: StateFlow<List<MessageData>> get() = _messageData

    init {
        fetchChannelData(channelId)
        fetchMessageData(channelId)
    }

    private fun fetchChannelData(channelId: String) {
        viewModelScope.launch {
            val data = channelService.getChannelById(channelId)
            if (data != null) {
                _channelData.value = data
            }
        }
    }


    private fun fetchMessageData(channelId: String) {
        viewModelScope.launch {
            messageService.getMessageData(channelId).collect { messages ->
                val sortedMessages = messages.sortedByDescending { message ->
                    java.util.Date(message.timestamp.toLong())
                }
                val messagesWithUsernames = sortedMessages.map { message ->
                    val user = userService.getUserDataById(message.author)
                    message.author = user?.username ?: message.author
                    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                    message.timestamp = sdf.format(java.util.Date(message.timestamp.toLong()))
                    message
                }
                _messageData.value = messagesWithUsernames
            }
        }
    }
}

class ConversationViewModelFactory(private val channelService: ChannelService, private val messageService: MessageService, private val userService: UserService, private val channelId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom( ConversationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return  ConversationViewModel(channelService, messageService, userService, channelId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}