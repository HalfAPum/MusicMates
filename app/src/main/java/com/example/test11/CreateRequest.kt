package com.example.test11

import com.google.gson.annotations.SerializedName

data class CreateRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("host_id")
    val hostId: Int,
)