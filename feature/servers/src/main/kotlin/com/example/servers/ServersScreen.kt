package com.example.servers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.mockChannels
import com.example.data.mockServers
import com.example.data.service.ChannelService
import com.example.data.service.ServerService
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.ChannelData
import com.example.model.ChannelListener
import com.example.model.ServerData
import com.example.ui.UserHead
import com.google.firebase.firestore.FirebaseFirestore

@Composable
internal fun ServersRoute(
    onChannelClick: (String) -> Unit,
    onCreateServerClick: () -> Unit,
    onCreateChannelClick: (String) -> Unit
) {
    val serverService = ServerService(FirebaseFirestore.getInstance())
    val channelService = ChannelService(FirebaseFirestore.getInstance())
    val serversViewModel: ServersViewModel = viewModel(factory = ServersViewModelFactory(serverService, channelService))

    ServersScreen(
        servers = serversViewModel.servers.collectAsState().value,
        channels = serversViewModel.channels.collectAsState().value,
        listener = { channel -> onChannelClick(channel) },
        onCreateServerClick = onCreateServerClick,
        onCreateChannelClick = onCreateChannelClick
    )
}

@Composable
fun ServersScreen(
    servers: List<ServerData>,
    channels: Map<String, List<ChannelData?>>,
    listener: ChannelListener,
    onCreateServerClick: () -> Unit,
    onCreateChannelClick: (String) -> Unit
) {
    var selectedServerId by remember { mutableStateOf(servers.firstOrNull()?.id) }
    Background {
        Row {
            // Server icons
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                servers.forEach { server ->
                    Row {
                        Spacer(
                            modifier = Modifier
                                .width(4.dp)
                                .height(40.dp)
                                .padding(end = 2.dp)
                                .background(if (selectedServerId == server.id) MaterialTheme.colorScheme.onSurface else Color.Transparent)
                        )
                        UserHead(
                            id = server.id,
                            name = server.name,
                            modifier = Modifier
                                .clickable { selectedServerId = server.id }
                        )
                    }
                }
                // Add server button
                IconButton(
                    onClick = { onCreateServerClick() },
                ) {
                    Icon(
                        imageVector = Icons.Add,
                        contentDescription = "Add Server"
                    )
                }
            }
            // Server details
            servers.find { it.id == selectedServerId }?.let { server ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                    modifier = Modifier
                        .padding(0.dp, 16.dp, 32.dp, 0.dp)
                        .weight(0.8f)
                        .fillMaxSize()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row (
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ServerName(name = server.name)
                            IconButton(
                                onClick = { onCreateChannelClick(server.id) },
                            ) {
                                Icon(
                                    imageVector = Icons.Add,
                                    contentDescription = "Add Server"
                                )
                            }
                        }
                        channels[selectedServerId]?.forEach { channelData ->
                            channelData?.let {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(start = 32.dp, top = 8.dp)
                                        .clickable {
                                            listener.onChannelSelected(it.id)
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServerName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
    )
}

@Preview
@Composable
fun PreviewServersScreen() {
    CosmeaTheme {
        ServersScreen(
            servers = mockServers,
            channels = mockChannels,
            listener = { channel -> println("Channel clicked: $channel") },
            onCreateServerClick = { println("Create server clicked") }
        ) { println("Create channel clicked") }
    }
}

@Preview
@Composable
fun PreviewServersScreenDark() {
    CosmeaTheme(darkTheme = true) {
        ServersScreen(
            servers = mockServers,
            channels = mockChannels,
            listener = { channel -> println("Channel clicked: $channel") },
            onCreateServerClick = { println("Create server clicked") }
        ) { println("Create channel clicked") }
    }
}
