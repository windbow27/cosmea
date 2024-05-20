package com.example.cosmea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cosmea.ui.App
import com.example.cosmea.ui.rememberAppState
import com.example.designsystem.theme.CosmeaTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CosmeaTheme {
                val appState = rememberAppState()
                App(appState)
            }
        }
        FirebaseApp.initializeApp(this)

//        val user = UserData("cheesedz", "nghia", "chisnghia@gmail.com",
//            "",mutableListOf(), mutableListOf(),
//        )
//        val server =  ServerData(
//            adminId = user.id,
//            name = "Loi choi",
//            avatar = null,
//            members = mutableListOf(),
//            channels = mutableListOf()
//        )
//        val channel1 = ChannelData(
//            name = "Category 1",
//            adminId = user.id,
//            serverId = server.id,
//            members = mutableListOf(),
//            messages = mutableListOf()
//        )
//        val channel2 = ChannelData(
//            name = "Category 2",
//            adminId = user.id,
//            serverId = server.id,
//            members = mutableListOf(),
//            messages = mutableListOf()
//        )
//        val message = MessageData(user.id, "26fc3ff5c", "How are you")
//        val profile = ProfileData("nghia", "08-06-2004", "avatar.png", "Fuck this life", user.id)
//        val userService = UserService(FirebaseFirestore.getInstance())
//        val serverService = ServerService(FirebaseFirestore.getInstance())
//        val channelService = ChannelService(FirebaseFirestore.getInstance())
//        val messageService = MessageService(FirebaseDatabase.getInstance())
//        val pushNotificationService = PushNotificationService()
//        lifecycleScope.launch {
//            userService.addUserData(user)
//            userService.getUserDataById("a29866353")
//            userService.getUserDataByUsername("cheesedz")
//            userService.updateUserData("User1", userData)
//            userService.deleteUserDataById("User1")
//            userService.updateUserProfile("a29866353", profile)
//            userService.getUserProfile("a29866353")
//            serverService.addServerData(server)
//            serverService.getServerDataById("c8d7bfb00")
//            serverService.getAdminId("c8d7bfb00")
//            channelService.addChannel(channel2, "9d3fb2ef0")
//            channelService.addChannel("c8d7bfb00",  channel2, "c8d7bfb00")
//            channelService.addMember("c8d7bfb00",  "a67d56694", user.id)
//            serverService.addMember("c8d7bfb00", user.id)
//            serverService.getAllMembers("c8d7bfb00")
            //ChannelService.deleteCategory("5dbf0d697","bce7d151b", user.id)
//            userService.verifyLoginInfo("windbow", "123")
//            messageService.addMessageData(channel2.id, message)
//            messageService.deleteMessageData("1a0dc0ea6")
//            messageService.getMessageData(channel2.id)
//            pushNotificationService.getFCMToken()
//            messageService.getAllFCMToken("0d5f056e1")
//        }
    }
}