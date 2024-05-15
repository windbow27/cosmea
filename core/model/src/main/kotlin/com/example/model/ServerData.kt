package com.example.model

class ServerData(
    var adminId: String,
    var name: String,
    var avatar: String? = null,
    var members: MutableList<String> = mutableListOf(),
    var channels: MutableList<String> = mutableListOf(),
    var id: String = generateId(adminId + name)
) {
    fun addMember(userId: String) {
        members.add(userId)
    }

    fun kickMember(userId: String) {
        members.remove(userId)
    }

    override fun toString(): String {
        return "ServerData(id='$id', adminId='$adminId', name='$name', avatar='$avatar', members=$members, channels=$channels"
    }
}