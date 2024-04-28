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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.mockServers
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.ChannelListener
import com.example.model.ServerData
import com.example.ui.UserHead

@Composable
internal fun ServersRoute(
    onChannelClick: (String) -> Unit
) {
    ServersScreen(
        servers = mockServers
    ) { channel -> onChannelClick(channel) }
}
@Composable
fun ServersScreen(
    servers: List<ServerData>,
    listener: ChannelListener
) {
    var selectedServerId by remember { mutableStateOf(servers.first().avatar) }
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
                                .padding(end = 1.dp)
                                .background(if (selectedServerId == server.avatar) MaterialTheme.colorScheme.onSurface else Color.Transparent)
                        )
                        UserHead(
                            id = server.avatar,
                            name = server.name,
                            modifier = Modifier
                                .clickable { selectedServerId = server.avatar }
                        )
                    }
                }
            }
            // Server details
            servers.find { it.avatar == selectedServerId }?.let { server ->
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
                    Column (
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    ) {
                        ServerName(name = server.name)
                        server.categories.forEach { categoryData ->
                            ServerCategory(
                                name = categoryData.name,
                                channels = categoryData.channels.map { it.name },
                                listener = listener
                            )
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
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )
}

@Composable
fun ServerCategory(
    name: String,
    channels: List<String>,
    listener: ChannelListener
) {
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded = !expanded }
        ) {
            Icon(
                imageVector = if (expanded) Icons.ArrowDown else Icons.ArrowUp,
                contentDescription = "Expand/Collapse Icon",
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        if (expanded) {
            ServerChannel(channels, listener)
        }
    }
}

@Composable
fun ServerChannel(channels: List<String>, listener: ChannelListener) {
    channels.forEach { channel ->
        Text(
            text = channel,
            modifier = Modifier
                .padding(start = 32.dp, top = 8.dp)
                .clickable {
                    listener.onChannelSelected(channel)
                }
        )
    }
}

@Preview
@Composable
fun PreviewServersScreen() {
    CosmeaTheme {
        ServersScreen(
            servers = mockServers
        ) {
            // no-op
        }
    }
}
