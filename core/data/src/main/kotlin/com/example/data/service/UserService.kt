package com.example.data.service

import android.util.Log
import com.example.data.repo.UserRepository
import com.example.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class UserService(private val firestore: FirebaseFirestore): UserRepository {
    override suspend fun addUserData(userData: UserData) {
        try {
            firestore.collection("users").add(userData).await()
        } catch (e: Exception) {
            Log.e("ERROR","Error adding user data to Firestore: $e") // Re-throw with clear message
        }
        Log.d("USER", "Created user successfully")
    }

    override suspend fun getUserDataById(userId: String): UserData? {
        val user = firestore.collection("users").document(userId).get().await()
        if (user.exists()) {
            Log.d("USER", "Get user successfully")
            return user.toObject(UserData::class.java)
        }
        Log.e("ERROR", "User with id = $userId not found")
        return null
    }

    override suspend fun getUserDataByUsername(userName: String): QuerySnapshot? {
        val user = firestore.collection("users")
            .whereEqualTo("username", userName).get().await()
        if (!user.isEmpty) {
            Log.d("USER", "Get user successfully")
            return user
        }
        Log.e("ERROR", "User with username = $userName not found")
        return null
    }

    override suspend fun updateUserData(userId: String, userData: UserData) {
        val user = getUserDataById(userId)
        if (user != null) {
            firestore.collection("user").document(userId).set(userData)
            Log.d("USER", "Update user successfully")
        }
        Log.d("ERROR", "Update user failed")
    }

    override suspend fun deleteUserDataById(userId: String) {
        val user = getUserDataById(userId)
        if (user != null) {
            firestore.collection("user").document(userId).delete()
            Log.d("USER", "Delete user successfully")
        }
        Log.d("ERROR", "Delete user failed")
    }
}