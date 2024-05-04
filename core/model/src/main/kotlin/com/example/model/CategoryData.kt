package com.example.model

data class CategoryData(
    val id: String,
    val name: String,
    val members : List<UserData>,
    val channels: List<ChannelData>
)