package com.example.test11

import com.google.gson.annotations.SerializedName

data class CreateRequest(
    @SerializedName("id")
    val roomId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("host_id")
    val hostId: Int,
    @SerializedName("public")
    val public: Boolean,
)