package com.example.test11

import com.google.gson.annotations.SerializedName

data class AddUser (
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("room_id")
    val room_id: Int,
        )