package com.example.test11

import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("trackURL")
    val trackUrl : String,
    @SerializedName("roomID")
    val roomId : Int,
)