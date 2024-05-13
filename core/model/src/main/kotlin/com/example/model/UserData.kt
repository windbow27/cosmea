package com.example.model

import com.example.model.generateUserId

class UserData(
    var username: String,
    var password: String,
    var email: String,
    var image: String? = null,
    var joinedServers: MutableList<String>,
    var friends: MutableList<String>,
    var profile: ProfileData? = null,
    var id: String? = generateUserId(username),
)