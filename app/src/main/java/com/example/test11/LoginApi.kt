package com.example.test11

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    fun validateToken(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @POST("create-room")
    fun createRoom(@Header("Authorization") token: String,
        @Body createRequest: CreateRequest) : Call<Response<String>>

}