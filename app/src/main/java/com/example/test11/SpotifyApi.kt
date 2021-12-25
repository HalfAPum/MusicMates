package com.example.test11

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpotifyApi {

    @GET("tracks/{id}")
    fun getTrackInfo(@Header("Authorization") token: String,
                       @Path("id") trackId: String) : Call<SpotifyResponse>
}

data class SpotifyResponse(
    @SerializedName("name")
    val name: String
)