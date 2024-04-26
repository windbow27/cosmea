package com.example.servers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.ui.UserHead

@Composable
internal fun ServersRoute(
    onTopicClick: (String) -> Unit
) {
    ServersScreen()
}
@Preview
@Composable
fun ServersScreen(
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
                    Categories(name = "Category 1", channels = listOf("#channel-1", "#channel-2"))
                    Categories(name = "Category 2", channels = listOf("#channel-3", "#channel-4"))
                    Categories(name = "Category 3", channels = listOf("#channel-5", "#channel-6"))
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
fun Categories(
    name: String,
    channels: List<String>

) {
    var expanded by remember { mutableStateOf(false) }

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
            Channels(channels)
        }
    }
}

@Composable
fun Channels(channels: List<String>) {
    channels.forEach { channel ->
        Text(text = channel, modifier = Modifier.padding(start = 32.dp, top = 8.dp))
    }
}

