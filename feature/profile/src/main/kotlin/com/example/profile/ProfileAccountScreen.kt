package com.example.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.service.UserService
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme
import com.example.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun AccountManagementRoute(
    onPasswordChangeClick: () -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    AccountManagementScreen(onPasswordChangeClick,onBackClick, onLogoutClick)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountManagementScreen(
    onPasswordChangeClick: () -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sharedPref = context.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE)
    val idUser: String? = sharedPref.getString("currentUserId", null)
    var userData by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(idUser) {
        if (idUser != null) {
            userData = getCurrentAccountData(idUser,coroutineScope)
        }
    }
    Background {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(text = "Your Profile", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    userData?.username?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = { userData?.username = it },
                            label = {
                                Text(
                                    "User Name",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { /* Handle user name change */ }
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    userData?.email?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = { userData?.email = it },
                            label = { Text("Email", style = MaterialTheme.typography.titleMedium) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { /* Handle user name change */ }
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    userData?.password?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = { },
                            label = {
                                Text(
                                    "Password",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            visualTransformation = if (passwordVisible) {
                                // display password if passwordVisible is true
                                VisualTransformation.None
                            } else {
                                // hide password if passwordVisible is false
                                PasswordVisualTransformation()
                            },
                            trailingIcon = {
                                // display an icon to toggle password visibility
                                val icon = if (passwordVisible) Icons.Eye else Icons.EyeOff
                                Icon(
                                    imageVector = icon,
                                    contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                    modifier = Modifier.clickable {
                                        passwordVisible = !passwordVisible
                                    }
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Button(
                        onClick = onPasswordChangeClick,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Change Password")

                    }
                    Button(
                        onClick = onPasswordChangeClick,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
suspend fun getCurrentAccountData(id: String, coroutineScope: CoroutineScope): UserData? {
    var userData: UserData? = null
    coroutineScope.launch {
        val userService = UserService(FirebaseFirestore.getInstance())
        userData = userService.getUserDataById(id)
    }.join()
    return userData
}

@Composable
internal fun ChangePasswordRoute(
    onBackClick: () -> Unit,
) {
    ChangePasswordScreen(onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
) {
    var checkChangePassword = true
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sharedPref = context.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE)
    val idUser: String? = sharedPref.getString("currentUserId", null)
    var userData by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(idUser) {
        if (idUser != null) {
            userData = getCurrentAccountData(idUser,coroutineScope)
        }
    }

    var newPassword by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Background {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = { Text("Old Password") },
                    visualTransformation = if (passwordVisible) {
                        // display password if passwordVisible is true
                        VisualTransformation.None
                    } else {
                        // hide password if passwordVisible is false
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        // display an icon to toggle password visibility
                        val icon = if (passwordVisible) Icons.Eye else Icons.EyeOff
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            modifier = Modifier.clickable {
                                passwordVisible = !passwordVisible
                            }
                        )
                    }
                )
                if(userData?.password != oldPassword.toString() && oldPassword.toString() != "") {
                    checkChangePassword = false
                    Text(text = "Password is incorrect!",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
                else {
                    checkChangePassword = true
                    Spacer(modifier = Modifier.height(16.dp))
                }
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = if (passwordVisible) {
                        // display password if passwordVisible is true
                        VisualTransformation.None
                    } else {
                        // hide password if passwordVisible is false
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        // display an icon to toggle password visibility
                        val icon = if (passwordVisible) Icons.Eye else Icons.EyeOff
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            modifier = Modifier.clickable {
                                passwordVisible = !passwordVisible
                            }
                        )
                    }

                )
                if(oldPassword != confirmPassword && confirmPassword.toString() != "")
                {
                    checkChangePassword = false
                    Text(text = "Confirm Password is incorrect!",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
                else {
                    checkChangePassword = true
                    Spacer(modifier = Modifier.height(16.dp))
                }
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (newPassword.isNotEmpty()) {
                                onPasswordChange(newPassword)
                            }
                        }
                    ),
                    visualTransformation = if (passwordVisible) {
                        // display password if passwordVisible is true
                        VisualTransformation.None
                    } else {
                        // hide password if passwordVisible is false
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        // display an icon to toggle password visibility
                        val icon = if (passwordVisible) Icons.Eye else Icons.EyeOff
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            modifier = Modifier.clickable {
                                passwordVisible = !passwordVisible
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { if(checkChangePassword){onPasswordChange(newPassword) }}) {
                    Text("Change Password")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

fun onPasswordChange(password: String){

}

@Preview
@Composable
fun AccountManagementScreenPreview() {
    CosmeaTheme(darkTheme = false) {
        AccountManagementScreen(
            onPasswordChangeClick = {},
            onBackClick = {},
            onLogoutClick = {}
        )
    }
}

@Preview
@Composable
fun ChangePasswordScreenPreview() {
    CosmeaTheme(darkTheme = false) {
        ChangePasswordScreen(
            onBackClick = {}
        )
    }
}

@Preview
@Composable
fun ChangePasswordScreenDarkPreview() {
    CosmeaTheme(darkTheme = true) {
        ChangePasswordScreen(
            onBackClick = {}
        )
    }
}

@Preview
@Composable
fun AccountManagementScreenDarkPreview() {
    CosmeaTheme(darkTheme = true) {
        AccountManagementScreen(
            onPasswordChangeClick = {},
            onBackClick = {},
            onLogoutClick = {}
        )
    }
}