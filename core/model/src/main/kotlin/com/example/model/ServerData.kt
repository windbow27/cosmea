package com.example.model

class ServerData(
    var adminId: String,
    var name: String,
    var avatar: String? = null,
    var members: MutableList<String> = mutableListOf(),
    var channels: MutableList<String> = mutableListOf(),
    var id: String = generateId(adminId)
) {
    fun addMember(userId: String) {
        members.add(userId)
    }

    fun kickMember(userId: String) {
        members.remove(userId)
    }

    fun addChannel(channelId: String) {
        channels.add(channelId)
    }

    fun deleteChannel(channelId: String) {
        channels.remove(channelId)
    }
}