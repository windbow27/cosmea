package com.example.cosmea

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.cosmea.ui.App
import com.example.cosmea.ui.rememberAppState
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.UserData
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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
        val userData = UserData("User1", "User 1", "1", 1, listOf("Server1", "Server2"), listOf("1", "2", "3"), listOf())
        val firestore = FirebaseFirestore.getInstance()
        try {
            firestore.collection("users").add(userData)
            Log.d("USER", "Created user successfully")
        } catch (e: Exception) {
            Log.e("ERROR","Error adding user data to Firestore: $e") // Re-throw with clear message
        }
    }

}