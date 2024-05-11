package com.example.model

class ServerData(
    var id: String? = null,
    var adminId: String,
    var name: String,
    var avatar: String,
    var members: MutableList<UserData> = mutableListOf(),
    var categories: MutableList<CategoryData> = mutableListOf()
) {
    fun addMember(userData: UserData) {
        members.add(userData)
    }

    fun kickMember(userData: UserData) {
        members.remove(userData)
    }

    fun addCategory(categoryData: CategoryData) {
        categories.add(categoryData)
    }

    fun deleteCategory(categoryData: CategoryData) {
        categories.remove(categoryData)
    }
}