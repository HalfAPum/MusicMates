package com.example.test11

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

object WebService {

    var token: String = ""
    var spotifyToken: String = ""

    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()

    var isOnBack = false

    private const val BASE_URL = "https://nure-mates-backend.herokuapp.com/api/v1/"
    private const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"

    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val spotifyretrofitInstance = Retrofit.Builder()
        .baseUrl(SPOTIFY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val loginApi: LoginApi = retrofitInstance.create(LoginApi::class.java)

    val spotifyApi: SpotifyApi = spotifyretrofitInstance.create(SpotifyApi::class.java)

    var roomId = 0

    var userId = 0
}