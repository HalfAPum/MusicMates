package com.example.test11

import com.google.gson.annotations.SerializedName

data class UsersListResponse(
    @SerializedName("room")
    val users: UsersList,
)

data class UsersList(
    @SerializedName("Users")
    val users: List<Users>,
    @SerializedName("name")
val name: String,
    @SerializedName("Tracks")
    val tracksss: List<tracks>
)
data class Users(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("email")
    val email: String,
)

data class tracks(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("TrackURL")
    val tracks: String,
)