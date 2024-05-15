package com.example.data

import android.util.Log
import com.example.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FireBase(private val firestore: FirebaseFirestore) { // Inject Firestore

    suspend fun addUserData(userData: UserData): String {
        val documentReference = try {
            firestore.collection("users").add(userData).await()
        } catch (e: Exception) {
            throw Exception("Error adding user data to Firestore: $e") // Re-throw with clear message
        }
        return documentReference.id
    }

    suspend fun getUserDataById(userId: String): UserData? {
        val user = firestore.collection("users").document(userId).get().await()
        return if (user.exists()) {
            user.toObject(UserData::class.java)
        } else {
            null // Indicate user not found
        }
    }

    suspend fun getFriendsOfUser(userId: String): List<UserData> {
        val userDocument = firestore.collection("users").document(userId).get().await()
        if (userDocument.exists()) {
            val friendIds = userDocument["friends"] as List<String>? ?: emptyList()
            val friends = mutableListOf<UserData>()
            for (friendId in friendIds) {
                val friendDocument = firestore.collection("users").document(friendId).get().await()
                if (friendDocument.exists()) {
                    val friend = friendDocument.toObject(UserData::class.java)
                    if (friend != null) {
                        friends.add(friend)
                    } else {
                        Log.e("ERROR", "Failed to parse friend data for user: $userId")
                    }
                } else {
                    Log.d("TAG", "Friend document not found for user: $userId") // Log with less severe level
                }
            }
            return friends
        } else {
            throw Exception("User document not found")
        }
    }
}
