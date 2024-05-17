package com.example.data.repo

import com.example.model.ChannelData
import com.example.model.ServerData
import com.google.firebase.firestore.CollectionReference

interface ServerRepository {
    suspend fun addServerData(serverData: ServerData)
    suspend fun getServerDataById(serverId: String): ServerData?
    suspend fun updateServerData(serverId: String, serverData: ServerData)
    suspend fun deleteServerDataById(serverId: String)
    suspend fun getAllMembers(serverId: String): String?
    suspend fun getAdminId(serverId: String): String?
    suspend fun getAllChannels(serverId: String): List<ChannelData>?
    suspend fun addMember(serverId: String, userId: String)
    fun getAllServerData(): CollectionReference
}