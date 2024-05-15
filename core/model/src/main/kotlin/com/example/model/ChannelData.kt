package com.example.model

class ChannelData (
    var name: String,
    var adminId: String,
    var serverId: String,
    var members : MutableList<String> = mutableListOf(),
    var messages: MutableList<String> = mutableListOf(),
    var id: String = generateId(name + adminId)
) {
    fun addMember(userId: String) {
        members.add(userId)
    }

    fun addMessage(userId: String) {
        messages.add(userId)
    }
}

fun interface ChannelListener {
    fun onChannelSelected(channel: String)
}