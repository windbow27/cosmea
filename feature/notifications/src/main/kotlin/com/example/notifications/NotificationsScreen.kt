package com.example.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.Background
import com.example.model.ChannelListener
import com.example.ui.UserHead

@Composable
internal fun NotificationsRoute(
    onTopicClick: (String) -> Unit
) {
    NotificationsScreen()
    { channel -> onTopicClick(channel) }
}


@Composable
fun NotificationsScreen(listener: ChannelListener) {
    Background{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Notifications", style = MaterialTheme.typography.titleLarge)
            LazyColumn {
                items(notifications) { notification ->
                    NotificationItem(notification = notification,listener = listener)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification, listener: ChannelListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { listener.onChannelSelected(notification.userId)},
        verticalAlignment = Alignment.CenterVertically,

    ) {
        UserHead(id = notification.userId, name = notification.userName)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = notification.userName, style = MaterialTheme.typography.bodyLarge)
            Text(text = notification.message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class Notification(
    val userId: String,
    val userName: String,
    val message: String
)

val notifications = listOf(
    Notification(userId = "1", userName = "User 1", message = "You have a new message!"),
    Notification(userId = "2", userName = "User 2", message = "Your post was liked!"),
    Notification(userId = "3", userName = "User 3", message = "Tulali talula"),

)