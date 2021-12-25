package com.example.test11

import com.google.gson.annotations.SerializedName

data class UpdateRequest (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("public")
    val isPublic: Boolean,
    @SerializedName("auto")
    val isAuto: Boolean,
    @SerializedName("fixed")
    val fixed: Int,
    @SerializedName("max_users")
    val maxUsers: Int,
)