package com.example.data.repo

import com.example.model.CategoryData
import com.example.model.ServerData

interface ServerRepository {
    suspend fun addServerData(serverData: ServerData)
    suspend fun getServerDataById(serverId: String): String?
    suspend fun updateServerData(serverId: String, serverData: ServerData)
    suspend fun deleteServerDataById(serverId: String)
    suspend fun addCategory(serverId: String, categoryData: CategoryData)
    suspend fun getAllCategories(serverId: String): String?
    suspend fun getAllMembers(serverId: String): String?
}