package com.example.servers

import android.content.Context
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.service.ChannelService
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.ChannelData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun CreateChannelRoute(
    serverId : String,
    onBackPressed: () -> Unit,
    onCreateChannelClick: () -> Unit
)  {
    CreateChannelScreen(
        serverId = serverId,
        onBackPressed = onBackPressed,
        onCreateChannelClick = onCreateChannelClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChannelScreen(
    serverId: String,
    onBackPressed: () -> Unit,
    onCreateChannelClick: () -> Unit
) {
    var channelName by remember { mutableStateOf("new-channel") }
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE)
    val adminId = sharedPref.getString("currentUserId", null)
    val coroutineScope = rememberCoroutineScope()
    Background{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TopAppBar(
                    title = {},
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

            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Invite Code", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Share this code to invite friends to your server:",
                        style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    CopyableText(serverId)
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Text("Create a new channel", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = channelName,
                    onValueChange = { channelName = it },
                    label = { Text("Channel Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    coroutineScope.launch {
                        // Create channel
                        val newChannel = ChannelData(
                            adminId = adminId!!,
                            serverId = serverId,
                            name = channelName,
                            members = mutableListOf(adminId),
                            messages = mutableListOf()
                        )
                        addChannelData(newChannel, coroutineScope, adminId)
                    }
                    onCreateChannelClick()
                }) {
                    Text("Create Channel")
                }
            }
        }
    }
}

fun addChannelData(channelData: ChannelData, coroutineScope: CoroutineScope, currentUserId: String) {
    val channelService = ChannelService(FirebaseFirestore.getInstance())
    coroutineScope.launch {
        channelService.addChannel(channelData, currentUserId)
    }
}

@Composable
fun CopyableText(text: String) {
    val clipboardManager = LocalClipboardManager.current
    Text(
        text = text,
        modifier = Modifier.clickable {
            clipboardManager.setText(AnnotatedString(text))
        },
        style = MaterialTheme.typography.labelLarge
    )
}

@Preview
@Composable
fun CreateChannelScreenPreview() {
    CosmeaTheme {
        CreateChannelScreen("",{}, {})
    }
}

@Preview
@Composable
fun CreateChannelScreenDarkPreview() {
    CosmeaTheme(darkTheme = true) {
        CreateChannelScreen("",{}, {})
    }
}