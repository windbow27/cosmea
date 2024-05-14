package com.example.data.repo

import com.example.model.CategoryData

interface CategoryRepository {
    suspend fun getCategoryById(serverId: String, categoryId: String): CategoryData?
    suspend fun addCategoryIntoServerList(serverId: String, categoryId: String)
    suspend fun addCategory(serverId: String, categoryData: CategoryData, currentUserId: String)
    suspend fun deleteCategory(serverId: String, categoryId: String, currentUserId: String)
    suspend fun updateCategoryData(
        serverId: String,
        categoryId: String,
        categoryData: CategoryData,
        currentUserId: String
    )
}