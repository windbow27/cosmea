package com.example.conversation

import androidx.compose.runtime.toMutableStateList
import com.example.model.MessageData

class ConversationUiState(
    val channelName: String,
    val channelMembers: Int,
    initialMessageData: List<MessageData>
) {
    private val _messageData: MutableList<MessageData> = initialMessageData.toMutableStateList()
    val messageData: List<MessageData> = _messageData

    fun addMessage(msg: MessageData) {
        _messageData.add(0, msg)
    }
}
