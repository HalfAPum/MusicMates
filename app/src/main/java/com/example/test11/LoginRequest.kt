package com.example.test11

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("id_token")
    val idToken: String
)

data class LoginResponse(
    @SerializedName("tokens_pair")
    val tokensPair: TokensPair,
    @SerializedName("user")
    val user: User
)

data class TokensPair(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("code")
    val code: String
)

data class User(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String
)