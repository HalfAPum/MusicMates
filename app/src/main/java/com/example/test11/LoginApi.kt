package com.example.test11

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    fun validateToken(@Body loginRequest: LoginRequest) : Call<LoginResponse>
}