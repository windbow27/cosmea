package com.example.data

import com.example.data.repo.UserRepository
import com.example.data.service.UserService
import com.example.model.UserData
import com.google.android.gms.tasks.Task
import com.google.common.base.CharMatcher.any
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import io.mockk.mockk as mockk

class FirebaseTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var userService: UserService

    @Before
    fun setUp() {
        firestore = FirebaseFirestore.getInstance() // Initialize firestore directly
        userService = UserService(firestore)
//        MockKAnnotations.init(this)
    }

    @Test
    fun `testAddUserData_success`() = runBlocking {
        coEvery { userService.addUserData(UserData("User1", "User 1", "1", 1, listOf("Server1", "Server2"), listOf("1", "2", "3"), listOf())) }

        val userData = UserData("User1", "User 1", "1", 1, listOf("Server1", "Server2"), listOf("1", "2", "3"), listOf())
        userService.addUserData(userData)

        verify { firestore.collection("user").add(userData) }
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
