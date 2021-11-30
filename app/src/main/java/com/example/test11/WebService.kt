package com.example.test11

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebService {

    var token: String = ""

    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()


    private const val BASE_URL = "https://nure-mates-backend.herokuapp.com/api/v1/"

    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val loginApi: LoginApi = retrofitInstance.create(LoginApi::class.java)
}