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
import androidx.compose.material3.MaterialTheme
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
internal fun CreateServerRoute(
    onBackPressed: () -> Unit
)  {
    CreateServerScreen(
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServerScreen(
    onBackPressed: () -> Unit,
) {
    var serverName by remember { mutableStateOf("My Server") }
    Background {
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Create Your Server",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            IconButton(onClick = { /* Handle upload image */ }) {
                Icon(
                    imageVector = Icons.Add,
                    contentDescription = "Upload Server Image"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = serverName,
                onValueChange = { serverName = it },
                label = { Text("Server Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { /* Handle create server */ }) {
                Text("Create Server")
            }
        }
    }
}

@Preview
@Composable
fun CreateServerScreenPreview() {
    CosmeaTheme {
        CreateServerScreen(onBackPressed = {})
    }
}

@Preview
@Composable
fun CreateServerRoutePreview() {
    CosmeaTheme(darkTheme = true) {
        CreateServerRoute(onBackPressed = {})
    }
}