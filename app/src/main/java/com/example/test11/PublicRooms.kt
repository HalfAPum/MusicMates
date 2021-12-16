package com.example.test11

import com.google.gson.annotations.SerializedName

data class LISTPUBLICROOMS(
    val list: List<PublicRooms>
)

data class PublicRooms (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)