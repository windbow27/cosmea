package com.example.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.Background
import com.example.designsystem.theme.CosmeaTheme

@Composable
fun RegisterScreen() {
    Background {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign up to Cosmea",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = "", // replace with your variable
                onValueChange = {}, // replace with your function
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "", // replace with your variable
                onValueChange = {}, // replace with your function
                label = { Text("Your email") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "", // replace with your variable
                onValueChange = {}, // replace with your function
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "", // replace with your variable
                onValueChange = {}, // replace with your function
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {}, // replace with your function
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Register")
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    CosmeaTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        RegisterScreen()
    }
}

@Preview
@Composable
fun PreviewRegisterScreenDark() {
    CosmeaTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        RegisterScreen()
    }
}
