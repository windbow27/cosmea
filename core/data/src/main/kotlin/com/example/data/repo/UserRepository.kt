package com.example.data.repo

import com.example.model.ProfileData
import com.example.model.UserData

interface UserRepository {
    suspend fun addUserData(userData: UserData): String?
    suspend fun getUserDataById(userId: String): UserData?
    suspend fun getUserDataByUsername(userName : String): String?
    suspend fun updateUserData(userId: String, userData: UserData)
    suspend fun deleteUserDataById(userId: String)
    suspend fun checkUsernameAvailability(userName: String): Boolean
    suspend fun updateUserProfile(userId: String, profileData: ProfileData)
    suspend fun checkEmailAvailability(email: String): Boolean
    suspend fun verifyLoginInfo(userName: String, password: String): Boolean
    suspend fun getUserIdByUsername(userName: String): String?
    suspend fun getUserProfile(userId: String): ProfileData?
    fun addFCMToken(token: String, userId: String)
    suspend fun getFCMToken(userId: String): String
    suspend fun getUsernameById(userId: String): String
    suspend fun addFriendRequest(currentUserId: String, friendId: String)
    suspend fun removeFriendRequest(currentUserId: String, friendId: String)

    suspend fun acceptFriendRequest(currentUserId: String, friendId: String)
}