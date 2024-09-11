package com.brandy.cooperativeapp.data.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String? = null, throwable: Throwable? = null, data: T? = null) :
        NetworkResult<T>(data, message, throwable)
}