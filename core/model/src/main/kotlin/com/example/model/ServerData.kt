package com.example.model

class ServerData(
    var adminId: String,
    var name: String,
    var avatar: String? = null,
    var members: MutableList<UserData> = mutableListOf(),
    var categories: MutableList<String> = mutableListOf(),
    var id: String = generateId(adminId)
) {
    fun addMember(userData: UserData) {
        members.add(userData)
    }

    fun kickMember(userData: UserData) {
        members.remove(userData)
    }

    fun addCategory(categoryId: String) {
        categories.add(categoryId)
    }

    fun deleteCategory(categoryId: String) {
        categories.remove(categoryId)
    }
}