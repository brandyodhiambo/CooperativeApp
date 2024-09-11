package com.brandy.cooperativeapp.domain.model


data class Login(
    val email: String?,
    val firstName: String?,
    val gender: String?,
    val id: Int?,
    val image: String?,
    val lastName: String?,
    val refreshToken: String?,
    val token: String,
    val username: String?
)
