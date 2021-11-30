package com.example.test11

import com.google.gson.annotations.SerializedName

data class UpdateHost (
    @SerializedName("id")
    val id: Int,
    @SerializedName("host_id")
    val hostId: Int,
)