package com.example.data.repo

import com.example.model.CategoryData
import com.example.model.ServerData
import com.example.model.UserData

interface ServerRepository {
    suspend fun addServerData(serverData: ServerData)
    suspend fun getServerDataById(serverId: String): ServerData?
    suspend fun updateServerData(serverId: String, serverData: ServerData)
    suspend fun deleteServerDataById(serverId: String)
//    suspend fun addCategory(serverId: String, categoryData: CategoryData)
    suspend fun getAllCategories(serverId: String): List<CategoryData>?
    suspend fun getAllMembers(serverId: String): String?
    suspend fun addMember(serverId: String, userData: UserData)
    suspend fun getAdminId(serverId: String): String?
}