package com.example.common.chatGPT

import android.util.Log
import androidx.compose.runtime.MutableState
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChatGPTClient {
    //retrofit no care, care about function
    private const val BASE_URL = "https://api.openai.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val chatGPTApi: ChatGPTApiService by lazy {
        retrofit.create(ChatGPTApiService::class.java)
    }

    //remember the conversation since each api call is treat as single talk by gpt
    val conversation: MutableList<Message> = mutableListOf()

    fun chatWithGPT(chatMessage: String, nextStep: (Message) -> Unit) {
        val message = Message(role = "user", content = chatMessage)
        conversation.add(message)
        val request = ChatGPTRequest(model = "gpt-3.5-turbo-16k", messages = conversation)

        val call = chatGPTApi.chatWithGPT(request)
        call.enqueue(object : Callback<ChatGPTResponse> {
            override fun onResponse(call: Call<ChatGPTResponse>, response: Response<ChatGPTResponse>) {
                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    chatResponse?.choices?.firstOrNull()?.let {
                        //add new message into conversation
                        conversation.add(it.message)
                        nextStep(it.message)
                    }
                    val reply = chatResponse?.choices?.firstOrNull()?.message?.content ?: "No response"
                    Log.e("reply", reply)

                } else {
                    val reply = "Request failed with code: ${response.code()}"
                    Log.e("reply", reply)
                }
            }

            override fun onFailure(call: Call<ChatGPTResponse>, t: Throwable) {
                val reply = "Request failed: ${t.message}"
                Log.e("reply", reply)
            }
        })
    }
}