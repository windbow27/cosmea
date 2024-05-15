package com.example.data.repo

import com.example.model.ChannelData

interface ChannelRepository {
    suspend fun getChannelById(serverId: String, channelId: String): ChannelData?
//    suspend fun getChannelById(channelId: String): ChannelData?
    suspend fun addChannelIntoServerList(serverId: String, channelId: String)
    suspend fun addChannel(serverId: String, channelData: ChannelData, currentUserId: String)
    suspend fun deleteChannel(serverId: String, channelId: String, currentUserId: String)
    suspend fun updateChannelData(
        serverId: String,
        channelId: String,
        channelData: ChannelData,
        currentUserId: String
    )
    suspend fun addMember(serverId: String, channelId: String, userId: String)
}