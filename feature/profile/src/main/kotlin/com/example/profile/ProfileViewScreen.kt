package com.example.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.Background

@Composable
internal fun ProfileViewRoute(
    onBackClick: () -> Unit
) {
    ProfileViewScreen(onBackClick)
}


@Preview
@Composable
fun ProfileViewScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    // State for holding user input
    val userNameState = remember { mutableStateOf("User Name") }
    val emailState = remember { mutableStateOf("user@example.com") }
    val passwordState = remember { mutableStateOf("") }
    // State for holding avatar resource ID
    val avatarResource = remember { mutableStateOf(R.drawable.avatar_default) }

    Background {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Nút Back
                Button(
                    onClick ={ onBackClick()},
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text("Back")
                }
            }
            Image(
                painter = painterResource(id = avatarResource.value),
                contentDescription = "User Avatar",
                modifier = Modifier.size(120.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )

            Text(text = "User Name", style = MaterialTheme.typography.titleLarge)

            // Button to change avatar (you can implement your own logic)
            Button(onClick = { /* Handle changing avatar */ }) {
                Text("Change Avatar")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Text field for user name
                OutlinedTextField(
                    value = userNameState.value,
                    onValueChange = { userNameState.value = it },
                    label = { Text("User Name", style = MaterialTheme.typography.bodyLarge) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle user name change */ }
                    )
                )

                // Text field for email
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("Email", style = MaterialTheme.typography.bodyLarge) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle email change */ }
                    )
                )

                // Text field for password
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Password", style = MaterialTheme.typography.bodyLarge) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle password change */ }
                    )
                )

                // Button to save changes
                Button(
                    onClick = {
                        // Handle saving changes
                    }
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewEditProfileScreen() {
//    ProfileViewScreen()
//}
