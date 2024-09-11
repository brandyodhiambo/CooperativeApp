package com.brandy.cooperativeapp.domain.repository

import com.brandy.cooperativeapp.data.network.request.LoginRequest
import com.brandy.cooperativeapp.data.utils.NetworkResult
import com.brandy.cooperativeapp.domain.model.Login

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): NetworkResult<Login>
}