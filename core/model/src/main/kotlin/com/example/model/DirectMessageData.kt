package com.example.model

data class DirectMessage(
    val channelId : String,
    val friendId: String,
    val friendUsername: String,
    val lastMessage: String
)