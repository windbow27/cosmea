package com.example.model

class CategoryData(
    var id: String,
    var name: String,
    var members : MutableList<UserData> = mutableListOf(),
    var channels: MutableList<ChannelData> = mutableListOf()
) {
    fun addMember(userData: UserData) {
        members.add(userData)
    }

    fun addChanel(channelData: ChannelData) {
        channels.add(channelData)
    }
}