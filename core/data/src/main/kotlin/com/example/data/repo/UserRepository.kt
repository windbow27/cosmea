package com.example.data.repo

import com.example.model.ProfileData
import com.example.model.UserData
import com.google.firebase.firestore.QuerySnapshot

interface UserRepository {
    suspend fun addUserData(userData: UserData): String?
    suspend fun getUserDataById(userId: String): String?
    suspend fun getUserDataByUsername(userName : String): String?
    suspend fun updateUserData(userId: String, userData: UserData)
    suspend fun deleteUserDataById(userId: String)
    suspend fun checkUsernameAvailability(userName: String): Boolean
    suspend fun updateUserProfile(userId: String, profileData: ProfileData)
    suspend fun checkEmailAvailability(email: String): Boolean
    suspend fun verifyLoginInfo(userName: String, password: String): Boolean
    suspend fun getUserIdByUsername(userName: String): String?
}