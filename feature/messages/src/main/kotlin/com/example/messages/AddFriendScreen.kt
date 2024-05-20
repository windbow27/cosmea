package com.example.messages

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.service.UserService
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun AddFriendRoute(
    onBackPressed: () -> Unit
) {
    AddFriendScreen(
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendScreen(
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE)
    val userId = sharedPref.getString("currentUserId", null)
    var friendUsername by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Background {
        LazyColumn {
            // Create a server
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopAppBar(
                        title = {
                            Text(text = "Add Friends", style = MaterialTheme.typography.titleLarge)
                        },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add by Username",
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = friendUsername,
                        onValueChange = { friendUsername = it },
                        label = { Text("Enter a username") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Add a Button for sending the friend request
                    Button(
                        onClick = {
                            println()
                            if (userId != null) {
                                addFriendRequest(userId, friendUsername, coroutineScope)
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Send Friend Request")
                    }
                }
            }
        }
    }
}

fun addFriendRequest(currentUserId: String, friendUsername: String, coroutineScope: CoroutineScope) {
    val userService = UserService(FirebaseFirestore.getInstance())
    coroutineScope.launch {
        val friendId = userService.getUserIdByUsername(friendUsername)
        if (friendId != null) {
            userService.addFriendRequest(currentUserId, friendId)
        }
    }
}

@Preview
@Composable
fun AddFriendScreenPreview() {
    CosmeaTheme() {
        AddFriendScreen(
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun AddFriendScreenDarkPreview() {
    CosmeaTheme(darkTheme = true) {
        AddFriendScreen(
            onBackPressed = {}
        )
    }
}