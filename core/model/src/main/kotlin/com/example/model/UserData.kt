package com.example.model

class UserData(
    var username: String,
    var password: String,
    var email: String,
    var joinedServers: MutableList<String>? = null,
    var friends: List<String>? = null,
    var profile: MutableList<String>? = null,
    var id: String = generateUserId(username),
)