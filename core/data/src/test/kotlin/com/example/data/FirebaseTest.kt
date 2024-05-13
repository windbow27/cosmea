package com.example.data

import com.example.data.service.UserService
import com.example.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.coEvery
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import io.mockk.mockk as mockk

class FirebaseTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var userService: UserService

//    @Before
//    fun setUp() {
//        firestore = FirebaseFirestore.getInstance() // Initialize firestore directly
//        userService = UserService(firestore)
////        MockKAnnotations.init(this)
//    }

    @Test
    suspend fun testAddUserDataSuccess() {
        val mockUserData = UserData(
            "User1", "cheesedz", "123", "1", 1,
            mutableListOf("Server1", "Server2"), mutableListOf("1", "2", "3")
        )

        val mockUserService = mockk<UserService>()
        coEvery { mockUserService.addUserData(mockUserData) } returns mockUserData.toString()

        val result = mockUserService.addUserData(mockUserData)
        Assertions.assertEquals(mockUserData, result)
    }

//    @Test
//    fun `testAddUserData_failure`() = runBlocking {
//        val exception = Exception("Test exception")
//        coEvery { mockFirestore.collection("users").add(any()) } throws exception
//
//        val userData = UserData("User1", "User 1", "1", 1, listOf("Server1", "Server2"), listOf("1", "2", "3"), listOf())
//
//        try {
//            firebase.addUserData(userData)
//            fail("Expected an exception")
//        } catch (e: Exception) {
//            assert(e == exception) // Verify the expected exception
//        }
//    }

}