package com.example.model

data class DirectMessage(
    val id : String,
    val friendId: String,
    val friendUsername: String,
    val lastMessage: String
)