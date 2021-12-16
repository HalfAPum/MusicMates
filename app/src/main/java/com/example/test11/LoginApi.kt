package com.example.test11

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {

    @POST("login")
    fun validateToken(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @POST("create-room")
    fun createRoom(@Header("Authorization") token: String,
        @Body createRequest: CreateRequest) : Call<String>

    @GET("get-room/{room_id}")
    fun getUsersInRoom(@Header("Authorization") token: String,
        @Path("room_id") room_id: Int) : Call<UsersListResponse>

    @POST("update-room")
    fun updateHost(@Header("Authorization") token: String,
       @Body updateHost: UpdateHost) : Call<String>

    @GET("public-rooms")
    fun getRooms(@Header("Authorization") token: String) : Call<List<PublicRooms>>

    @PUT("room")
    fun addUser(@Header("Authorization") token: String,
        @Body addUser: AddUser
                 ) : Call<String>

    @POST("tracks")
    fun addTack(@Header("Authorization") token: String,
                @Body track: Track) : Call<String>

}