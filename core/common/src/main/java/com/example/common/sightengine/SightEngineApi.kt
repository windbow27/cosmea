package com.example.common.sightengine

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SightEngineApi {

    @Multipart
    @POST("check-workflow.json")
    fun checkImage(
        @Part file: MultipartBody.Part,
        @Part("workflow") models: RequestBody = RequestBody.create(MultipartBody.FORM, "wfl_g6cQUjKqQZXVVCzrSi7ZA"),
        @Part("api_user") apiUser: RequestBody = RequestBody.create(MultipartBody.FORM, "1507018594"),
        @Part("api_secret") apiSecret: RequestBody = RequestBody.create(MultipartBody.FORM, "aPYuNTxQ5b9oeJkWCJHn7BAmbQsVtDB7")
    ): Call<SightEngineResponse> // Adjust the response type based on SightEngine API response


}