package com.example.servers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.UserHead

@Composable
internal fun ServersRoute(
    onTopicClick: (String) -> Unit
) {
    ServersScreen()
}
@Preview
@Composable
fun ServersScreen() {
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
            modifier = Modifier
                .weight(0.8f)
                .fillMaxSize()
        ) {
            Column {
                ServerName(name = "Server Name")
                SearchBar()
                Channels()
            }
        }
    }
}

@Composable
fun ServerName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun Channels() {
    Column {
        // Replace with actual channels
        Text(text = "Channel 1", modifier = Modifier.padding(16.dp))
        Text(text = "Channel 2", modifier = Modifier.padding(16.dp))
    }
}