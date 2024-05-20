package com.example.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.service.UserService
import com.example.designsystem.component.Background
import com.example.model.ProfileData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun ProfileViewRoute(
    onBackClick: () -> Unit
) {
    ProfileViewScreen(onBackClick)
}

suspend fun getCurrentProfileData(id: String, coroutineScope: CoroutineScope): ProfileData? {
    var profileData: ProfileData? = null
    coroutineScope.launch {
        val userService = UserService(FirebaseFirestore.getInstance())
        profileData = userService.getUserProfile(id)
    }.join()
    return profileData
}



@SuppressLint("CoroutineCreationDuringComposition")
@Preview
@Composable
fun ProfileViewScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val avatarResource = remember { mutableStateOf(R.drawable.avatar_default) }

    val coroutineScope = rememberCoroutineScope()
    val sharedPref = context.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE)
    val idUser: String? = sharedPref.getString("currentUserId", null)
    var userData by remember { mutableStateOf<ProfileData?>(null) }

    LaunchedEffect(idUser) {
        if (idUser != null) {
            userData = getCurrentProfileData(idUser,coroutineScope)
        }
    }
    var displayNameState = remember { mutableStateOf("") }
    var dobState = remember { mutableStateOf("") }
    var bioState = remember { mutableStateOf("") }
    userData?.let {
        println(it.displayName)
        displayNameState = remember { mutableStateOf(userData?.displayName.toString()) }
        dobState = remember { mutableStateOf(userData?.dob.toString()) }
        bioState = remember { mutableStateOf(userData?.bio.toString()) }
    }



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
            imageUri?.let {
                if(Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                }
                else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }

                bitmap.value?.let { btm ->
                    println(bitmap)
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Transparent)
                    )
                }
            }
            if(bitmap.value == null) {
                Image(
                    painter = painterResource(id = avatarResource.value),
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(120.dp),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }

            Text(text = userData?.displayName.toString() , style = MaterialTheme.typography.titleLarge)

            // Button to change avatar (you can implement your own logic)
            Button(onClick = { /* Handle changing avatar */
                launcher.launch("image/*")
            }) {
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
                displayNameState.let {
                    OutlinedTextField(
                        value = displayNameState.value,
                        onValueChange = { displayNameState.value = it },
                        label = { Text("Display name", style = MaterialTheme.typography.bodyLarge) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { /* Handle user name change */ }
                        )
                    )
                }

                // Text field for email
                OutlinedTextField(
                    value = dobState.value,
                    onValueChange = { dobState.value = it },
                    label = { Text("Date of birth", style = MaterialTheme.typography.bodyLarge) },
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
                    value = bioState.value,
                    onValueChange = { bioState.value = it },
                    label = { Text("Bio", style = MaterialTheme.typography.bodyLarge) },
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


suspend fun getCurrentUserData(id: String): ProfileData? {
    val userService = UserService(FirebaseFirestore.getInstance())
    val user = userService.getUserProfile(id)
    return user
}


//@Preview
//@Composable
//fun PreviewEditProfileScreen() {
//    ProfileViewScreen()
//}
