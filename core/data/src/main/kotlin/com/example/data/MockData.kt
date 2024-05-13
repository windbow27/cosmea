package com.example.data

import com.example.model.CategoryData
import com.example.model.ChannelData
import com.example.model.MessageData
import com.example.model.ServerData
import com.example.model.UserData

val mockUsers = mutableListOf(
    UserData("User 1","User 1", "1","1", null, mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 2","User 2", "2","2", null, mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData("User 3","User 3", "3","3", null, mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 4","User 4", "4", "4", null, mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 5","User 5", "5","5", null, mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
    UserData( "User 6","User 6", "6","6", null, mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")),
)

val mockDirectMessages = mutableListOf(
    ChannelData(
        id = "1",
        name = "DM 1",
        members = mockUsers,
        messages = mutableListOf(
            MessageData("1", "1" ,"User1", "Hello", "12:00"),
            MessageData("2","2","User2", "Hi", "12:01"),
            MessageData("3","3","User3", "Hey, what's up?", "12:02"),
            MessageData("4","4", "User1", "Nothing much", "12:03"),
            MessageData("5","5","User2", "Cool", "12:04"),
            MessageData("6","6","User3", "Yeah", "12:05"),
        )
    ),
    ChannelData(
        id = "2",
        name = "DM 1",
        members = mockUsers,
        messages = mutableListOf(
            MessageData("1", "1" ,"User1", "Hello", "12:00"),
            MessageData("2","2","User2", "Hi", "12:01"),
            MessageData("3","3","User3", "Hey, what's up?", "12:02"),
            MessageData("4","4", "User1", "Nothing much", "12:03"),
            MessageData("5","5","User2", "Cool", "12:04"),
            MessageData("6","6","User3", "Yeah", "12:05"),
        )
    ),
)

val mockChannels = mutableListOf(
    ChannelData(
        id = "Channel 1",
        name = "Channel 1",
        members = mockUsers,
        messages = mutableListOf(
            MessageData("1", "1" ,"User1", "Hello", "12:00"),
            MessageData("2","2","User2", "Hi", "12:01"),
            MessageData("3","3","User3", "Hey, what's up?", "12:02"),
            MessageData("4","4", "User1", "Nothing much", "12:03"),
            MessageData("5","5","User2", "Cool", "12:04"),
            MessageData("6","6","User3", "Yeah", "12:05"),
        )
    ),
    ChannelData(
        id = "Channel 2",
        name = "Channel 2",
        members = mockUsers,
        messages = mutableListOf(
            MessageData("1", "1" ,"User1", "Hello", "12:00"),
            MessageData("2","2","User2", "Hi", "12:01"),
            MessageData("3","3","User3", "Hey, what's up?", "12:02"),
            MessageData("4","4", "User1", "Nothing much", "12:03"),
            MessageData("5","5","User2", "Cool", "12:04"),
            MessageData("6","6","User3", "Yeah", "12:05"),
        )
    ),
    ChannelData(
        id = "Channel 3",
        name = "Channel 3",
        members = mockUsers,
        messages = mutableListOf(
            MessageData("1", "1" ,"User1", "Hello", "12:00"),
            MessageData("2","2","User2", "Hi", "12:01"),
            MessageData("3","3","User3", "Hey, what's up?", "12:02"),
            MessageData("4","4", "User1", "Nothing much", "12:03"),
            MessageData("5","5","User2", "Cool", "12:04"),
            MessageData("6","6","User3", "Yeah", "12:05"),
        )
    ),
)

val mockServers = mutableListOf(
    ServerData(
        id = "Server1",
        adminId = "User1",
        name = "Server 1",
        avatar = "1",
        members = mockUsers,
        categories = mutableListOf(
            CategoryData(
                id = "Category1",
                name = "Category 1",
                members = mockUsers,
                channels = mockChannels
            ),
            CategoryData(
                id = "Category2",
                name = "Category 2",
                members = mockUsers,
                channels = mockChannels
            )
        )
    ),
    ServerData(
        id = "Server2",
        adminId = "User1",
        name = "Server 2",
        avatar = "2",
        members = mockUsers,
        categories = mutableListOf(
            CategoryData(
                id = "Category3",
                name = "Category 3",
                members = mockUsers,
                channels = mockChannels
            ),
            CategoryData(
                id = "Category4",
                name = "Category 4",
                members = mockUsers,
                channels = mockChannels
            )
        )
    ),
)


