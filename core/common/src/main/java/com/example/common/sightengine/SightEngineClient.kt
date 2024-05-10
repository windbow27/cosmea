package com.example.common.sightengine

import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

object SightEngineClient {
    //retrofit no care, care about function
    private const val BASE_URL = "https://api.sightengine.com/1.0/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val sightEngineApi: SightEngineApi by lazy {
        retrofit.create(SightEngineApi::class.java)
    }

    //hàm này check ảnh, cái nextStep parameter là để xử lý giá trị true false của retrofit sau khi call xong
    fun checkImageForNSFW(imageUri: Uri, context: Context, nextStep: (Boolean) -> Unit) {
        val cacheDir = context.cacheDir
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = imageUri.let { contentResolver.openInputStream(it) }
        if(inputStream == null) {
            nextStep(false)
            return
        }
        val requestFile = try {
            val file = File(cacheDir, "temp_image")
            inputStream.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            RequestBody.create(MediaType.parse("image/*"), file)
        } catch (e: FileNotFoundException) {
            // Handle file not found error
            Log.e("Image File input", e.message.toString())
            nextStep(false)
            return
        }

        val filePart = MultipartBody.Part.createFormData("media", "temp_image", requestFile)

        val call = sightEngineApi.checkImage(filePart)

        call.enqueue(object: Callback<SightEngineResponse> {
            override fun onResponse(call: Call<SightEngineResponse>, response: Response<SightEngineResponse>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    val checkResponse = response.body()
                    if (checkResponse != null) {
                        Log.e("response", checkResponse.toString())
                    } else {
                        nextStep(false)
                        return
                    }
                    //check if image SFW
                    nextStep(checkResponse.summary.action == "reject")
                } else {
                    // Handle unsuccessful response (e.g., HTTP error)
                    val errorMessage = "HTTP Error: ${response.code()}"
                    // Log the error message
                    Log.e("ImageChecker", errorMessage)
                    nextStep(false)
                }
            }

            override fun onFailure(call: Call<SightEngineResponse>, t: Throwable) {
                // Handle network errors (e.g., connection timeout, no internet)
                val errorMessage = "Network Error: ${t.message}"
                // Log the error message
                Log.e("ImageChecker", errorMessage)
                nextStep(false)
            }
        })

    }

}