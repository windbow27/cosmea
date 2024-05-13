package com.example.model

class UserData(
    var id: String? = null,
    var username: String,
    var password: String,
    var email: String,
    var image: Int,//List<String>? = null,
    var joinedServers: List<String>,
    var friends: List<String>,
    var profile: ProfileData? = null
    // var status: String
//     var about: String? = null
)