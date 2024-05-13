package com.example.model

class UserData(
    var username: String,
    var password: String,
    var email: String,
    var joinedServers: MutableList<String>,
    var friends: List<String>,
    var profile: MutableList<String>? = null,
    var id: String? = generateUserId(username),
)