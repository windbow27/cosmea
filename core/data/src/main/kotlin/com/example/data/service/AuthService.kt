package com.example.data.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

class AuthService(private val firestore: FirebaseFirestore) {
    suspend fun verifyLoginInfo(userName: String, password: String): Boolean {
        val usernamesRef = firestore.collection("users")
            .whereEqualTo("username", userName).whereEqualTo("password", password)
        val querySnapshot = usernamesRef.get().await()
        if (!querySnapshot.isEmpty) {
            Log.d("FIRESTORE", "Login successfully")
            return true
        }
        Log.e("FIRESTORE", "Login info incorrect")
        return false
    }

    fun encryptPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = messageDigest.digest(password.toByteArray())

        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
}