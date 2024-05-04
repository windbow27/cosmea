package com.example.model

data class MessageData(
    val id: String,
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
    val authorImage: Int? = null
)