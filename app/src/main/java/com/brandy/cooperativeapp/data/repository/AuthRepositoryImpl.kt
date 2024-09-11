package com.brandy.cooperativeapp.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import com.brandy.cooperativeapp.data.local.Preference
import com.brandy.cooperativeapp.data.mapper.toDomain
import com.brandy.cooperativeapp.data.network.ApiService
import com.brandy.cooperativeapp.data.network.request.LoginRequest
import com.brandy.cooperativeapp.data.utils.NetworkResult
import com.brandy.cooperativeapp.data.utils.safeApiCall
import com.brandy.cooperativeapp.domain.model.Login
import com.brandy.cooperativeapp.domain.repository.AuthRepository

/*
* Implements the auth repository
* Takes in apiService for network calls
* And preference for storing user data and access token locally
* */

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val preference: Preference
): AuthRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun login(loginRequest: LoginRequest): NetworkResult<Login> {
        return safeApiCall {
            val result = apiService.login(loginRequest)
            preference.saveAccessToken(result.token)
            preference.saveUserdata(result.toDomain())
            result.toDomain()
        }
    }
}