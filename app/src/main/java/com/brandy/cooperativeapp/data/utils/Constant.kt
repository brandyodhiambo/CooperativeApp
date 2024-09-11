package com.brandy.cooperativeapp.data.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {
    const val BASE_URL = "https://dummyjson.com/"
    const val LOGIN = "auth/login"

    //Preference Constants
    const val COP_PREFERENCE = "cop_preference"
    val AUTH_KEY = stringPreferencesKey(name = "auth_key")
    val USER_DATA = stringPreferencesKey("user_data")
}