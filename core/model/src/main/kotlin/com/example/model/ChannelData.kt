package com.example.model

data class ChannelData (
    val id: String,
    val name: String,
    val members : List<UserData>,
    val messages: List<MessageData>
)

interface ChannelListener {
    fun onChannelSelected(channel: String)
}