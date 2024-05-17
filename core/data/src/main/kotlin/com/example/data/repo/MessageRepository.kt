package com.example.data.repo

import com.example.model.MessageData

interface MessageRepository {
    suspend fun getMessageData(channelId: String): List<MessageData>
    //    suspend fun getReceiver(messageId: String)
    suspend fun deleteMessageData(messageId: String)
    suspend fun addMessageData(channelId: String, messageData: MessageData)
    suspend fun addMessageIntoChannelList(channelId: String, messageId: String)
}