package com.brandy.cooperativeapp.data.utils


import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
suspend fun <T : Any> safeApiCall(
    cacheData: T? = null,
    dispatchers: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T,
): NetworkResult<T> {
    return try {
        val result = withContext(dispatchers) {
            apiCall()
        }
        NetworkResult.Success(data = result)
    } catch (e: IOException) {
        Timber.e("IO: ${e.message}")
        NetworkResult.Error(
            data = cacheData,
            message = "Could not reach the server, please check your internet connection!",
        )
    } catch (e: HttpException) {
        Timber.e("HTTP: ${e.message}")
        if (e.hashCode() == 400) {
            return NetworkResult.Error(message = "Invalid Credentials")
        }
        NetworkResult.Error(message = "An Unknown error occurred, please try again!")
    } catch (e: Exception) {
        Timber.e("Exception: ${e.message}")
        NetworkResult.Error(
            data = cacheData,
            message = "An Unknown error occurred, please try again!",
        )
    }
}
