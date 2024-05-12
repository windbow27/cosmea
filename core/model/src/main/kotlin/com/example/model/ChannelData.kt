package com.example.model

class ChannelData (
    var id: String,
    var name: String,
    var members : MutableList<UserData> = mutableListOf(),
    var messages: MutableList<MessageData> = mutableListOf()
) {
    fun addMember(userData: UserData) {
        members.add(userData)
    }

    fun addMessage(messageData: MessageData) {
        messages.add(messageData)
    }
}

fun interface ChannelListener {
    fun onChannelSelected(channel: String)
}