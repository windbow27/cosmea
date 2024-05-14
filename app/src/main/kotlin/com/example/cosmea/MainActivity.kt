package com.example.cosmea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cosmea.ui.App
import com.example.cosmea.ui.rememberAppState
import com.example.data.mockChannels
import com.example.data.mockUsers
import com.example.data.service.ServerService
import com.example.data.service.UserService
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.CategoryData
import com.example.model.ProfileData
import com.example.model.ServerData
import com.example.model.UserData
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

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
        val userData = UserData("chisnghia", "nghia", "123", mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3"))
        val profile = ProfileData("nghia", "08-06-2004", "avatar.png", "Fuck this life")
        val server =  ServerData(
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
        )
        val category = CategoryData(
            id = "Category5",
            name = "Category 5",
            members = mockUsers,
            channels = mockChannels
        )
        val userService = UserService(FirebaseFirestore.getInstance())
        val serverService = ServerService(FirebaseFirestore.getInstance())
//        lifecycleScope.launch {
//            userService.addUserData(userData)
////            userService.getUserDataById("User1")
////            userService.getUserDataByUsername("cheesedz")
////            userService.updateUserData("User1", userData)
////            userService.deleteUserDataById("User1")
////            userService.updateUserProfile("1", profile)
////            serverService.addServerData(server)
////            serverService.getAllCategories("Server2")
////            serverService.getAllMembers("Server2")
////            serverService.addCategory("Server2", category)
//            userService.verifyLoginInfo("windbow", "123")
//        }
    }
}