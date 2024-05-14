package com.example.model

class CategoryData(
    var name: String,
    var members : MutableList<UserData> = mutableListOf(),
    var channels: MutableList<String> = mutableListOf(),
    var id: String = generateId(name)
) {
    fun addMember(userData: UserData) {
        members.add(userData)
    }

    fun addChanel(channelId: String) {
        channels.add(channelId)
    }
}