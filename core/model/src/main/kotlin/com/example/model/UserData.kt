package com.example.model

class UserData(
    var id: String? = null,
    var username: String,
    var password: String,
    var email: String,
    var image: Int? = null,
    var joinedServers: MutableList<String>,
    var friends: MutableList<String>,
    var profile: ProfileData? = null
    // var status: String
//     var about: String? = null
)