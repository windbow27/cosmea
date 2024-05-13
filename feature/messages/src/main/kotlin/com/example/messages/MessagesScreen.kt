package com.example.messages

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.Background
import com.example.model.ChannelListener
import com.example.ui.SearchToolbar
import com.example.ui.UserHead

@Composable
internal fun MessagesRoute(
    onChannelClick: (String) -> Unit,
) {
    MessagesScreen(

    ) { channel -> onChannelClick(channel) }
}
@Composable
fun MessagesScreen(
    listener: ChannelListener
) {
    var searchQuery = remember { mutableStateOf("") }
    Background {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Messages", style = MaterialTheme.typography.titleLarge
            )
            SearchToolbar(
                searchQuery = searchQuery.value,
                onSearchQueryChanged = {searchQuery.value = it},
                onSearchTriggered = {},
            )
            val filtered_messages = messages.filter { it.userName.contains(searchQuery.value,ignoreCase = true) }
            LazyColumn {
                items(filtered_messages) { message ->
                    MessageItem(message = message, listener = listener)
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: Message, listener: ChannelListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { listener.onChannelSelected(message.userId) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserHead(id = message.userId, name = message.userName)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = message.userName, style = MaterialTheme.typography.bodyLarge)
            Text(text = message.lastMessage, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
data class Message(
    val userId: String,
    val userName: String,
    val lastMessage: String
)

val messages = listOf(
    Message(userId = "1", userName = "User 1", lastMessage = "Hello!"),
    Message(userId = "2", userName = "User 2", lastMessage = "How are you?"),
    // Add more messages here
)

@Preview
@Composable
fun PreviewMessagesScreen() {
    MessagesScreen {
        // Handle channel selection
    }
}