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
    }
}