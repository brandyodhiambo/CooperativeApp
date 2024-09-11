package com.brandy.cooperativeapp.data.mapper

import com.brandy.cooperativeapp.data.network.response.LoginResponseDto
import com.brandy.cooperativeapp.domain.model.Login

internal fun LoginResponseDto.toDomain() = Login(
    email = email,
    firstName = firstName,
    gender = gender,
    id = id,
    image = image,
    lastName = lastName,
    refreshToken = refreshToken,
    token = token,
    username = username
)