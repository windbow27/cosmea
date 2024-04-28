package com.example.model

class UserData (
    val id: String,
    val name: String,
    val email: String,
    val image: Int? = null,
    val joinedServers : List<String>,
    val directMessages: List<String>,
    val friends: List<UserData>
    // val status: String
    // val about: String
)