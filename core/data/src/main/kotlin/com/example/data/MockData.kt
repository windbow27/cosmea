package com.example.data

import com.example.model.ChannelData
import com.example.model.ServerData
import com.example.model.UserData

val mockUsers = mutableListOf(
    UserData("User 1","User 1", "1", mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 2","User 2", "2", mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData("User 3","User 3", "3", mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 4","User 4", "4",  mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 5","User 5", "5", mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 6","User 6", "6", mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
)

val mockDirectMessages = mutableListOf(
    ChannelData(
        name = "DM 1",
        adminId = "",
        members = mutableListOf(),
        messages = mutableListOf()) ,
    ChannelData(
        name = "DM 1",
        adminId = "",
        members = mutableListOf(),
        messages = mutableListOf()
    ),
)

val mockChannel = ChannelData(
    name = "Channel 1",
    adminId = "",
    members = mutableListOf(),
    messages = mutableListOf()
)

val mockChannels = mutableListOf(
    ChannelData(
        id = "Channel 1",
        adminId = "",
        name = "Channel 1",
        members = mutableListOf(),
        messages = mutableListOf()
    ),
    ChannelData(
        name = "Channel 2",
        adminId = "",
        members = mutableListOf(),
        messages = mutableListOf()
    ),
    ChannelData(
        name = "Channel 3",
        adminId = "",
        members = mutableListOf(),
        messages = mutableListOf()
    ),
)

val mockServers = mutableListOf(
    ServerData(
        id = "Server1",
        adminId = "User1",
        name = "Server 1",
        avatar = "1",
        members = mutableListOf(),
        channels = mutableListOf()
    ),
    ServerData(
        adminId = "User1",
        name = "Server 2",
        avatar = "2",
        members = mutableListOf(),
        channels = mutableListOf()
    ),
)


