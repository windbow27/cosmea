package com.example.model

class UserData(
    var username: String,
    var password: String,
    var email: String,
    var image: List<String>? = null,
    var joinedServers: List<String>,
    var friends: List<String>,
    var profile: ProfileData? = null,
    var id: String? = generateUserId(username),
)