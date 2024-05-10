package com.example.model

data class UserData(
    var id: String? = null,
    var username: String,
    var password: String,
    var email: String,
    var image: Int? = null,
    var joinedServers: List<String>,
    var friends: List<String>,
    // var status: String
//     var about: String? = null
)