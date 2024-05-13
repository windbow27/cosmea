package com.example.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ui.UserHead

@Composable
internal fun ProfileRoute(
    onTopicClick: (String) -> Unit
) {
    ProfileScreen()
}

@Preview
@Composable
fun ProfileScreen() {
    val navController: NavHostController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAvatar()
        Text(text = "User Name", style = MaterialTheme.typography.titleLarge)
        Button(onClick = { /* Handle Add Status */
        }) {
            Text("Register new account")
        }
        Button(onClick = { /* Handle Edit Profile */ }) {
            Text("Edit Profile")
        }
        AboutMeCard()
    }
}

@Composable
fun UserAvatar() {
    UserHead(id = "1", name = "User")
}

@Composable
fun AboutMeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "About Me", style = MaterialTheme.typography.titleMedium)
            // Replace with actual About Me text
            Text(text = "This is a placeholder for the About Me text.", style = MaterialTheme.typography.bodyLarge)
            Friends()
        }
    }
}

@Composable
fun Friends() {
    // Replace with actual implementation
}