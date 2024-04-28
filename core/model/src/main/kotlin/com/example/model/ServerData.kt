package com.example.model

data class ServerData(
    val id: String,
    val name: String,
    val avatar: String,
    val members: List<UserData>,
    val categories: List<CategoryData>
)