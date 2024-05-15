package com.example.data.repo

import com.example.model.ChannelData

interface ChannelRepository {
    suspend fun getChannelById(channelId: String): ChannelData?
    suspend fun addChannelIntoServerList(serverId: String, channelId: String)
    suspend fun addChannel(channelData: ChannelData, currentUserId: String)
    suspend fun deleteChannel(channelId: String, currentUserId: String)
    suspend fun updateChannelData(
        channelId: String,
        channelData: ChannelData,
        currentUserId: String
    )
    suspend fun addMember(channelId: String, userId: String)
}