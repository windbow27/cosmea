package com.example.messages

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
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
import com.example.data.mockDirectMessages
import com.example.data.service.ChannelService
import com.example.data.service.MessageService
import com.example.data.service.UserService
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.model.ChannelListener
import com.example.model.DirectMessage
import com.example.model.ProfileData
import com.example.ui.SearchToolbar
import com.example.ui.UserHead
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun MessagesRoute(
    onChannelClick: (String) -> Unit,
    onAddFriendScreenClick: () -> Unit
) {
    val userService = UserService(FirebaseFirestore.getInstance())
    val channelService = ChannelService(FirebaseFirestore.getInstance())
    val messageService = MessageService(FirebaseDatabase.getInstance())
    val userId = LocalContext.current.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE).getString("currentUserId", null) ?: ""
    val messagesViewModel : MessagesViewModel = viewModel(factory = MessagesViewModelFactory(userService, channelService, messageService, userId))

    MessagesScreen(
        directMessages = messagesViewModel.directMessages.collectAsState().value,
//        avatars = messagesViewModel.profiles.collectAsState().value,
        listener = { channelId -> onChannelClick(channelId) },
        onAddFriendScreenClick = onAddFriendScreenClick
    )
}
@Composable
fun MessagesScreen(
    directMessages: List<DirectMessage>,
//    avatars: List<String>,
    listener: ChannelListener,
    onAddFriendScreenClick: () -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    Background {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Messages", style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = { onAddFriendScreenClick() }) {
                    Icon(Icons.AddFriend, contentDescription = "Add Friends")
                }
            }
            SearchToolbar(
                searchQuery = searchQuery.value,
                onSearchQueryChanged = {searchQuery.value = it},
                onSearchTriggered = {},
            )
            val filteredMessages = directMessages.filter { it.friendUsername.contains(searchQuery.value,ignoreCase = true) }
            GPTItem(listener = listener)
            LazyColumn {
                items(filteredMessages) { message ->
                    MessageItem(message = message, listener = listener)
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MessageItem(message: DirectMessage, listener: ChannelListener) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { listener.onChannelSelected(message.channelId) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserHead(id = message.friendId, name = message.friendUsername/*, avatarUrl = messagesViewModel.profiles.collectAsState().value.toString()*/)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = message.friendUsername, style = MaterialTheme.typography.bodyLarge)
            Text(text = message.lastMessage, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun GPTItem(listener: ChannelListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { listener.onChannelSelected("GPT") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserHead(id = "GPT", name = "Cosmea Bot" /*avatarUrl = "https://firebasestorage.googleapis.com/v0/b/cosmea-69930.appspot.com/o/avatar%2Fa29866353.jpg?alt=media&token=10f931e2-7159-4555-9027-473f8d29a3e5"*/)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = "Cosmea Bot", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Let's have a chat", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun PreviewMessagesScreen() {
    MessagesScreen(
        directMessages = mockDirectMessages,
        listener = { channelId -> println("Channel clicked: $channelId") },
        onAddFriendScreenClick = { println("Add friend clicked") }
    )
}