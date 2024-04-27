package com.example.servers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.Background
import com.example.designsystem.theme.CosmeaTheme
import com.example.ui.UserHead

@Composable
internal fun ServersRoute(
    onChannelClick: (String) -> Unit
) {
    ServersScreen(
        listener = object : ChannelListener {
            override fun onChannelSelected(channel: String) {
                onChannelClick(channel)
            }
        }
    )
}
@Composable
fun ServersScreen(
    listener: ChannelListener
) {
    Background {
        Row (
        ) {
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                UserHead(id = "1", firstName = "a", lastName = "a")
                UserHead(id = "2", firstName = "a", lastName = "a")
                UserHead(id = "3", firstName = "a", lastName = "a")
                UserHead(id = "4", firstName = "a", lastName = "a")
                UserHead(id = "5", firstName = "a", lastName = "a")
            }
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
                    ServerName(name = "Server Name")
                    Categories(
                        name = "Category Name",
                        channels = listOf("Channel1", "Channel2", "Channel3"),
                        listener = listener
                    )
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

@Preview
@Composable
fun PreviewServersScreen() {
    CosmeaTheme {
        ServersScreen(
            listener = object : ChannelListener {
                override fun onChannelSelected(channel: String) {
                }
            }
        )
    }
}