package com.example.test11

import java.io.Serializable

data class RoomSerializedData(
    val roomName: String,
    val isPrivate: Boolean = true,
    val maxMembers: Int = 32,
    val preferableMusic: String = "",
    val isAutoFill: Boolean = true,
) : Serializable