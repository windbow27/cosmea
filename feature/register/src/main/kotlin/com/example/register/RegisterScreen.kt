package com.example.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.service.UserService
import com.example.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
internal fun RegisterRoute(
    onRegisterClick: () -> Unit,
    redictToLogin: () -> Unit
) {
    RegisterScreen(onRegisterClick, redictToLogin)
}

@Preview
@Composable
fun RegisterScreen(onRegisterClick: () -> Unit, redictToLogin: () -> Unit) {
    var userState by remember { mutableStateOf(TextFieldValue()) }
    var emailState by remember { mutableStateOf(TextFieldValue()) }
    var passwordState by remember { mutableStateOf(TextFieldValue()) }
    var checkPasswordState by remember { mutableStateOf(TextFieldValue()) }
    var userService: UserService = UserService(FirebaseFirestore.getInstance())
    val coroutineScope = rememberCoroutineScope()
    var isUsernameAvailable by remember { mutableStateOf(true) }
    var isEmailAvailable by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5E399F)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign up with Email",
            color = Color.White,
            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Get chatting with friends and family today by signing up for our chat app!",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            modifier = Modifier.padding(horizontal = 36.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            OutlinedTextField(
                value = userState,
                onValueChange = {userState = it
                    coroutineScope.launch {
                        isUsernameAvailable = userService.checkUsernameAvailability(it.text)
                    }},
                placeholder = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true
            )
            if (!isUsernameAvailable) {
                Text(
                    text = "Username is not available",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            OutlinedTextField(
                value = emailState,
                onValueChange = {emailState = it
                    coroutineScope.launch {
                        isEmailAvailable = userService.checkEmailAvailability(it.text)
                    }
                                },
                placeholder = { Text("Your Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true
            )
            if(isEmailAvailable) {
                Text(text = "Email is not available",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                    )
            }

            OutlinedTextField(
                value = passwordState,
                onValueChange = { passwordState = it},
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = checkPasswordState,
                onValueChange = { checkPasswordState = it},
                placeholder = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            if(checkPasswordState != passwordState) {
                Text(text = "Password is incorrect!",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                    )
            }
            val OnRegister:()->Unit = {
                var userdata = UserData(
                    userState.text,
                    passwordState.text,
                    emailState.text,
                    null,
                    null,
                    null
                )
                println(userdata.id)
                coroutineScope.launch {
                    if(userService.addUserData(userdata) != null) {
                        onRegisterClick()
                    }
                }
            }
            Button(
                onClick =  /* Handle register */
                          OnRegister
                ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors( Color(0xFFC1A5F4))
            ) {
                Text(text = "Create an account", color = Color.White, fontSize = 16.sp)
            }
            Text(
                text = "Already have an account? Login",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 90.dp, bottom = 10.dp, end = 30.dp)
                    .clickable { /* Handle navigate to login screen */
                        redictToLogin()
                    }
            )
        }
    }
}
