package com.example.cosmea

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.cosmea.ui.App
import com.example.cosmea.ui.rememberAppState
import com.example.data.service.UserService
import com.example.designsystem.theme.CosmeaTheme
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
        val userData = UserData("User1", "cheesedz", "123","1", 1,
            listOf("Server1", "Server2"), listOf("1", "2", "3"))
        val userService = UserService(FirebaseFirestore.getInstance())
        lifecycleScope.launch {
            userService.addUserData(userData)
            userService.getUserDataById("User1")
            userService.getUserDataByUsername("cheesedz")
            userService.updateUserData("User1", userData)
            userService.deleteUserDataById("User1")
        }
    }
}