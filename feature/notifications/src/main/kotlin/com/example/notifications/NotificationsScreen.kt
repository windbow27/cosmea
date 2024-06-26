package com.example.notifications

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.mockNotifications
import com.example.data.service.ChannelService
import com.example.data.service.UserService
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.ChannelData
import com.example.model.Notification
import com.example.model.ProfileData
import com.example.ui.UserHead
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun NotificationsRoute(
) {
    val userService = UserService(FirebaseFirestore.getInstance())
    val userId = LocalContext.current.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE).getString("currentUserId", null) ?: ""
    Log.d("NotificationsRoute", "userId: $userId")
    val notificationsViewModel: NotificationsViewModel = viewModel(factory = NotificationViewModelFactory(userService, userId))

    NotificationsScreen(
        notifications = notificationsViewModel.notifications.collectAsState().value,
        userId = userId
    )
}

@Composable
fun NotificationsScreen(
    notifications: List<Notification>,
    userId: String
) {
    Background{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Notifications", style = MaterialTheme.typography.titleLarge)
            LazyColumn {
                items(notifications) { notification ->
                    NotificationItem(notification = notification, userId = userId)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    userId: String
) {
    val userService = UserService(FirebaseFirestore.getInstance())
    val coroutineScope = rememberCoroutineScope()
    var currentUserProfile by remember { mutableStateOf<ProfileData?>(null) }

    LaunchedEffect(userId) {
        coroutineScope.launch {
            currentUserProfile = userService.getUserProfile(userId)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        currentUserProfile?.avatar?.let { UserHead(id = notification.userId, name = notification.userName, avatarUrl = it) }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = notification.userName, style = MaterialTheme.typography.bodyLarge)
            Text(text = notification.message, style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = {
             acceptFriendRequest(userId = userId, friendId = notification.userId, coroutineScope)        }) {
            Icon(Icons.Accept, contentDescription = "Accept")
        }
        IconButton(onClick = {
            declineFriendRequest(userId = userId, friendId = notification.userId, coroutineScope)
        }) {
            Icon(Icons.Decline, contentDescription = "Decline")
        }
    }
}

fun acceptFriendRequest(userId: String, friendId: String, coroutineScope: CoroutineScope) {
    val userService = UserService(FirebaseFirestore.getInstance())
    val channelService = ChannelService(FirebaseFirestore.getInstance())
    val newDirectMessage = ChannelData(
        name = "Private Chat",
        serverId = "",
        adminId = userId + friendId,
        members = mutableListOf(userId, friendId),
        messages = mutableListOf()
    )
    coroutineScope.launch {
        userService.acceptFriendRequest(userId, friendId)
        userService.removeFriendRequest(userId, friendId)
        channelService.addDirectMessage(newDirectMessage, userId, friendId)
    }

}

fun declineFriendRequest(userId: String, friendId: String, coroutineScope: CoroutineScope) {
    val userService = UserService(FirebaseFirestore.getInstance())
    coroutineScope.launch {
        userService.removeFriendRequest(userId, friendId)
    }
}

@Preview
@Composable
fun NotificationsScreenPreview() {
    CosmeaTheme(darkTheme = false) {
        NotificationsScreen(
            notifications = mockNotifications,
            userId = "1"
        )
    }
}

@Preview
@Composable
fun NotificationsDarkScreenPreview() {
    CosmeaTheme(darkTheme = true) {
        NotificationsScreen(
            notifications = mockNotifications,
            userId = "1"
        )
    }
}

