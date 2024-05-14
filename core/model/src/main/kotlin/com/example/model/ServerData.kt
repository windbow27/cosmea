package com.example.model

class ServerData(
    var adminId: String,
    var name: String,
    var avatar: String? = null,
    var members: MutableList<UserData> = mutableListOf(),
    var channels: MutableList<String> = mutableListOf(),
    var id: String = generateId(adminId + name)
) {
    fun addMember(userData: UserData) {
        members.add(userData)
    }

    fun kickMember(userData: UserData) {
        members.remove(userData)
    }

    override fun toString(): String {
        return "ServerData(id='$id', adminId='$adminId', name='$name', avatar='$avatar', members=$members, channels=$channels"
    }
}