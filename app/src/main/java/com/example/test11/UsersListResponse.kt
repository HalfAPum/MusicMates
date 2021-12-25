package com.example.test11

import com.google.gson.annotations.SerializedName

data class UsersListResponse(
    @SerializedName("room")
    val users: UsersList,
)

data class UsersList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Users")
    val users: List<Users>,
    @SerializedName("name")
    val name: String,
    @SerializedName("Tracks")
    val tracksss: List<tracks>,
    @SerializedName("invite_token")
    val privateAccessToken: String,
    @SerializedName("public")
    val isPublic: Boolean,
    @SerializedName("auto")
    val isAuto: Boolean,
    @SerializedName("fixed")
    val fixed: Int,
    @SerializedName("max_users")
    val maxUsers: Int,
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