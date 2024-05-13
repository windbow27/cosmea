package com.example.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.data.service.UserService
import com.example.designsystem.theme.inversePrimaryDark
import com.example.profile.navigation.navigateToProfile
import com.example.register.navigation.navigateToRegister
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
internal fun LoginRoute(
    onTopicClick: (String) -> Unit
) {
    LoginActivity()
}

@Preview
@Composable
fun LoginActivity() {
    var userState by remember { mutableStateOf(TextFieldValue()) }
    var passwordState by remember { mutableStateOf(TextFieldValue()) }
    var userService: UserService = UserService(FirebaseFirestore.getInstance())
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = inversePrimaryDark)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log in to Cosmea",
            color = Color.White,
            fontSize = 34.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Welcome back! Sign in using social account or email to continue us",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(46.dp))
        Text(
            text = "-------------------- OR --------------------",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FacebookLoginButton(image = R.drawable.facebook_icon)
            Spacer(modifier = Modifier.width(20.dp))
            GoogleLoginButton(image = R.drawable.google_icon)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = userState,
                onValueChange = {userState = it},
                placeholder = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = passwordState,
                onValueChange = {passwordState = it},
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            val coroutineScope = rememberCoroutineScope()
            val OnClickLogin:()->Unit = {
                coroutineScope.launch {
                    var acceptLogin =
                        userService.verifyLoginInfo(userState.text, passwordState.text)
                    if(acceptLogin) {
                        navController.navigateToProfile()
                    }
                    else {
                        //
                    }
                }
            }
            Button(
                onClick = OnClickLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log In")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Don't have an account? Register",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { /*9 Handle navigation to registration */
                        navController.navigateToRegister()
                    }),
                fontSize = 16.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun FacebookLoginButton(image: Int) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { /* Handle social login click */ },
        contentAlignment = Alignment.Center
    )
    {
        val icon: Painter = painterResource(id = image)
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun GoogleLoginButton(image: Int) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { /* Handle social login click */ },
        contentAlignment = Alignment.Center
    )
    {
        val icon: Painter = painterResource(id = image)
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}

