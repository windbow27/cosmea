package com.example.model

class UserData(
    var username: String,
    var password: String,
    var email: String,
    var joinedServers: List<String>,
    var friends: List<String>,
    var profile: ProfileData? = null,
    var id: String? = generateUserId(username),
)