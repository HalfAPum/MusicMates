package com.example.test11

import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("track_URL")
    val trackUrl : String
)