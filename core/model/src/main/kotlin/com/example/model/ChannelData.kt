package com.example.model

class ChannelData(
    var name: String = "",
    var serverId: String = "",
    var adminId: String = "",
    var members: MutableList<String> = mutableListOf(),
    var messages: MutableList<String> = mutableListOf(),
    var id: String = generateId(name + adminId + serverId)
) {
    fun addMember(userId: String) {
        members.add(userId)
    }

    fun addMessage(userId: String) {
        messages.add(userId)
    }

    override fun toString(): String {
        return "ChannelData(id='$id', name='$name', members=$members, messages=$messages)"
    }
}

fun interface ChannelListener {
    fun onChannelSelected(channel: String)
}