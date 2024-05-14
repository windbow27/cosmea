package com.example.servers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import com.example.designsystem.theme.CosmeaTheme

@Composable
internal fun CreateChannelRoute(
    onBackPressed: () -> Unit,
    onCreateChannelClick: () -> Unit
)  {
    CreateChannelScreen(
        onBackPressed = onBackPressed,
        onCreateChannelClick = onCreateChannelClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChannelScreen(
    onBackPressed: () -> Unit,
    onCreateChannelClick: () -> Unit
) {
    var channelName by remember { mutableStateOf("") }
    Background{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            TextField(
                value = channelName,
                onValueChange = { channelName = it },
                label = { Text("Channel Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { onCreateChannelClick() }) {
                Text("Create Channel")
            }
        }
    }
}

@Preview
@Composable
fun CreateChannelScreenPreview() {
    CosmeaTheme {
        CreateChannelScreen({}, {})
    }
}

@Preview
@Composable
fun CreateChannelScreenDarkPreview() {
    CosmeaTheme(darkTheme = true) {
        CreateChannelScreen({}, {})
    }
}