package com.example.cosmea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.cosmea.ui.App
import com.example.cosmea.ui.rememberAppState
import com.example.data.service.ChannelService
import com.example.data.service.ServerService
import com.example.data.service.UserService
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.ChannelData
import com.example.model.ProfileData
import com.example.model.ServerData
import com.example.model.UserData
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

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
        val user = UserData("quachday", "cuong", "123",
            mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")
        )
        val channel1 = ChannelData(
            name = "Category 1",
            adminId = user.id,
            members = mutableListOf(),
            messages = mutableListOf()
        )
        val channel2 = ChannelData(
            name = "Category 2",
            adminId = user.id,
            members = mutableListOf(),
            messages = mutableListOf()
        )
        val profile = ProfileData("nghia", "08-06-2004", "avatar.png", "Fuck this life")
        val server =  ServerData(
            adminId = user.id,
            name = "Loi choi",
            avatar = null,
            members = mutableListOf(),
            channels = mutableListOf()
        )
        val userService = UserService(FirebaseFirestore.getInstance())
        val serverService = ServerService(FirebaseFirestore.getInstance())
        val channelService = ChannelService(FirebaseFirestore.getInstance())
        lifecycleScope.launch {
//            userService.addUserData(user)
//            userService.getUserDataById("26fc3ff5c")
//            userService.getUserDataByUsername("cheesedz")
//            userService.updateUserData("User1", userData)
//            userService.deleteUserDataById("User1")
//            userService.updateUserProfile("1", profile)
//            serverService.addServerData(server)
//            serverService.getServerDataById("c8d7bfb00")
//            channelService.addChannel("c8d7bfb00",  channel1, "9d3fb2ef0")
//            channelService.addChannel("c8d7bfb00",  channel2, "c8d7bfb00")
//            channelService.addMember("c8d7bfb00",  "a67d56694", user.id)
//            serverService.addMember("c8d7bfb00", user.id)
//            serverService.getAllMembers("c8d7bfb00")
            //ChannelService.deleteCategory("5dbf0d697","bce7d151b", user.id)
//            userService.verifyLoginInfo("windbow", "123")
        }
    }
}