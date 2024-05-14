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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.service.UserService
import com.example.designsystem.component.Background
import com.example.designsystem.theme.CosmeaTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
internal fun LoginRoute(
    onLoginClick : () -> Unit,
    redirectToRegister: () -> Unit
) {
    LoginScreen(
        onLoginClick = onLoginClick,
        redirectToRegister
    )
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    redirectToRegister: () -> Unit
) {
    var userNameState by remember { mutableStateOf(TextFieldValue()) }
    var passwordState by remember { mutableStateOf(TextFieldValue()) }
    var loginError by remember { mutableStateOf(false) }
    var userService: UserService = UserService(FirebaseFirestore.getInstance())
    var passwordVisible by remember { mutableStateOf(false) }

    Background() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log in to Cosmea",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 34.sp
            )
            Spacer(modifier = Modifier.height(80.dp))
//        Text(
//            text = "Welcome back! Sign in using social account or email to continue us",
//            color = Color.White,
//            fontSize = 20.sp,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(horizontal = 16.dp)
//        )
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
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(20.dp)
            ) {
                OutlinedTextField(
                    value = userNameState,
                    onValueChange = {userNameState = it},
                    placeholder = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = passwordState,
                    onValueChange = {passwordState = it},
                    placeholder = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) {
                        // Hiển thị mật khẩu nếu passwordVisible là true
                        VisualTransformation.None
                    } else {
                        // Ẩn mật khẩu nếu passwordVisible là false
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        // Nút để thay đổi trạng thái hiển thị mật khẩu
                        Image(
                            painter = painterResource(if (passwordVisible) R.drawable.ic_hide_password else R.drawable.ic_hide_password),
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            modifier = Modifier.clickable {
                                passwordVisible = !passwordVisible
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                val coroutineScope = rememberCoroutineScope()
                val onLogin:()->Unit = {
                    coroutineScope.launch {
                        val acceptLogin =
                            userService.verifyLoginInfo(userNameState.text, passwordState.text)
                        if(acceptLogin) {
//                            val currentUserId = userService.getUserIdByUsername(userNameState.text)
//
//                            val sharedPrefs = LocalContext.current.getSharedPreferences("user", 0)

                            onLoginClick()

                        }
                        else {
                            loginError = true
                        }
                    }
                }
                Button(
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Log In")
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (loginError) {
                    Text(
                        text = "Username or password is incorrect",
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = "Don't have an account? Register",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { /*9 Handle navigation to registration */
                            redirectToRegister()
                        }),
                    fontSize = 16.sp,
                    textAlign = TextAlign.End
                )
            }
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

@Preview
@Composable
fun LoginScreenPreview() {
    CosmeaTheme() {
        LoginScreen(onLoginClick = {}, redirectToRegister = {})
    }
}

@Preview
@Composable
fun LoginScreenDarkPreview() {
    CosmeaTheme(darkTheme = true) {
        LoginScreen(onLoginClick = {}, redirectToRegister = {})
    }
}
