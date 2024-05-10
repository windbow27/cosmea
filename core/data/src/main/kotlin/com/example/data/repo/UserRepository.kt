package com.example.data.repo

import com.example.model.UserData
import com.google.firebase.firestore.QuerySnapshot

interface UserRepository {
    suspend fun addUserData(userData: UserData)
    suspend fun getUserDataById(userId: String): UserData?
    suspend fun getUserDataByUsername(userName : String): QuerySnapshot?
    suspend fun updateUserData(userId: String, userData: UserData)
    suspend fun deleteUserDataById(userId: String)
}