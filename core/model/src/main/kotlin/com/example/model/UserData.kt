package com.example.model

class UserData(
    var username: String,
    var password: String,
    var email: String,
    var phone: String? = null,
    var joinedServers: MutableList<String>? = null,
    var friends: MutableList<String>? = null,
    var pendingFriends: MutableList<String>? = null,
    var id: String = generateId(username),
    var profile: String = id,
    var fcmToken: String? = null,
)