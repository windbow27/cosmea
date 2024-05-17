package com.example.model

class UserData(
    var username: String,
    var password: String,
    var email: String,
    var joinedServers: MutableList<String>? = null,
    var friends: MutableList<String>? = null,
    var id: String = generateId(username),
    var profile: String = id,
)