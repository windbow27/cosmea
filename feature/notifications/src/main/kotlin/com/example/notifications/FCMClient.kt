package com.example.notifications

import android.util.Log
import com.example.data.service.UserService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

object FCMClient {
    private const val BASE_URL = "https://fcm.googleapis.com/fcm/send"

    val FCM_API_KEY = "AAAAj0b26RA:APA91bFHJS86ZyFJjpRxpe3fzEZ01oyunOWRDLPwcTt5_w9ULul2Ua11aovQ4nKYoAxYsTgJY1TqawMo_w-Uisj9ogidOKiYcnKoG_BCAMCMtMSB2CWPWOVBSbOlJ_SLIsiwQehB_AeR"

    suspend fun sendMessageNotification(message: String, currentUserId: String, tokens: List<String>) {
        val userService = UserService(FirebaseFirestore.getInstance())
        val username: String = coroutineScope {
            async {
                userService.getUsernameById(currentUserId)
            }.await()
        }.toString()
        Log.d("Username", "$username")
        if (tokens.isEmpty()) {
            Log.e("DEBUG", "No tokens available to send notifications")
            return
        }
        tokens.forEach {token ->
            try {
                print(token)
                print(message)
                val jsonObject = JSONObject()
                val notificationObject = JSONObject()
                notificationObject.put("title", "$username send to you a message")
                notificationObject.put("body", message)

                val dataObject = JSONObject()
                dataObject.put("userId", currentUserId)
                jsonObject.put("notification", notificationObject)
                jsonObject.put("data", dataObject)
                jsonObject.put("to", token)

                callAPI(jsonObject)
            } catch (exception: Exception) {
                Log.e("FCM API", "Error when call API $exception")
            }
        }
    }

    suspend fun sendFriendNotification(currentUserId: String, token: String) {
        val userService = UserService(FirebaseFirestore.getInstance())
        val username: String = coroutineScope {
            async {
                userService.getUsernameById(currentUserId)
            }.await()
        }.toString()
        Log.d("Username", "$username")
        if (token.isEmpty()) {
            Log.e("DEBUG", "No tokens available to send notifications")
            return
        }
        try {
            print(token)
            val jsonObject = JSONObject()
            val notificationObject = JSONObject()
            notificationObject.put("title", "$username send you a friend request")
            notificationObject.put("body", "")

            val dataObject = JSONObject()
            dataObject.put("userId", currentUserId)
            jsonObject.put("notification", notificationObject)
            jsonObject.put("data", dataObject)
            jsonObject.put("to", token)

            callAPI(jsonObject)
        } catch (exception: Exception) {
            Log.e("FCM API", "Error when call API $exception")
        }
    }

    fun callAPI(jsonObject: JSONObject) {
        val JSON: MediaType = MediaType.get("application/json; charset=utf-8")
        val client = OkHttpClient()
        val requestBody: RequestBody = RequestBody.create(JSON, jsonObject.toString())
        val request: Request = Request.Builder()
            .url(BASE_URL)
            .post(requestBody)
            .header("Authorization", "Bearer $FCM_API_KEY")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("FCM API", "Error when calling API: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { // Use 'use' to ensure response body is closed after usage
                    if (!response.isSuccessful) {
                        Log.e("FCM API", "Unexpected code $response")
                    } else {
                        response.let { responseBody ->
                            val responseBodyString = responseBody // Access the response body content
                            Log.d("FCM API", "Response received: $responseBodyString")
                        }
                    }
                }
            }
        })
    }
}