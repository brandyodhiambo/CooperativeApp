package com.brandy.cooperativeapp.data.network

import com.brandy.cooperativeapp.data.network.request.LoginRequest
import com.brandy.cooperativeapp.data.network.response.LoginResponseDto
import com.brandy.cooperativeapp.data.utils.Constant.LOGIN
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST(LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponseDto
}