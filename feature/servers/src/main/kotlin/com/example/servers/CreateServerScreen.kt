package com.example.servers

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.service.ServerService
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.ServerData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun CreateServerRoute(
    onBackPressed: () -> Unit,
    onCreateServerClick: () -> Unit,
    onJoinServerClick: () -> Unit
)  {
    CreateServerScreen(
        onBackPressed = onBackPressed,
        onCreateServerClick = onCreateServerClick,
        onJoinServerClick = onJoinServerClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServerScreen(
    onBackPressed: () -> Unit,
    onCreateServerClick: () -> Unit,
    onJoinServerClick: () -> Unit
) {
    var serverName by remember { mutableStateOf("My Server") }
    var serverCode by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE)
    val adminId = sharedPref.getString("currentUserId", null)
    val coroutineScope = rememberCoroutineScope()

    Background {
        LazyColumn {
            // Create a server
            item {
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

                    Spacer(modifier = Modifier.height(16.dp))

                    IconButton(onClick = { /* Handle upload image */ }) {
                        Icon(
                            imageVector = Icons.Add,
                            contentDescription = "Upload Server Image"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = serverName,
                        onValueChange = { serverName = it },
                        label = { Text("Server Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(onClick = {
                        val newServer = ServerData(
                            adminId = adminId!!,
                            name = serverName,
                            avatar = "https://example.com/avatar.jpg",
                        )
                        addServerData(newServer, coroutineScope)
                        onCreateServerClick()
                    }) {
                        Text("Create Server")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Join a server
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Join A Server",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = serverCode,
                        onValueChange = { serverCode = it },
                        label = { Text("Server Code") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(onClick = {
                        addMemberToServer(serverCode, adminId!!, coroutineScope)
                        onJoinServerClick()
                    }) {
                        Text("Join Server")
                    }
                }
            }
        }
    }
}

fun addServerData(serverData: ServerData, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val serverService = ServerService(FirebaseFirestore.getInstance())
        serverService.addServerData(serverData)
    }
}

fun addMemberToServer(serverId: String, memberId: String, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val serverService = ServerService(FirebaseFirestore.getInstance())
        serverService.addMember(serverId, memberId)
    }
}

@Preview
@Composable
fun CreateServerScreenPreview() {
    CosmeaTheme {
        CreateServerScreen(onBackPressed = {}, onCreateServerClick = {}, onJoinServerClick = {})
    }
}

@Preview
@Composable
fun CreateServerRoutePreview() {
    CosmeaTheme(darkTheme = true) {
        CreateServerRoute(onBackPressed = {}, onCreateServerClick = {}, onJoinServerClick = {})
    }
}