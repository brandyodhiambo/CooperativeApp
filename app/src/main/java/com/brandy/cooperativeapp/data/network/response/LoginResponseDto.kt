package com.brandy.cooperativeapp.data.network.response


import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("token")
    val token: String,
    @SerializedName("username")
    val username: String?
)