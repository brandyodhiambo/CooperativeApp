package com.brandy.cooperativeapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.brandy.cooperativeapp.data.utils.Constant.AUTH_KEY
import com.brandy.cooperativeapp.data.utils.Constant.USER_DATA
import com.brandy.cooperativeapp.domain.model.Login
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*
* Preference class which provides functions to save and get access token and user data
* */

class Preference (private val dataStore: DataStore<Preferences>, private val gson: Gson) {

    suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_KEY] = accessToken
        }
    }

    val getAccessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_KEY]
    }

    suspend fun saveUserdata(user: Login) {
        dataStore.edit { preferences ->
            preferences[USER_DATA] = gson.toJson(user)
        }
    }

    val getUserData: Flow<Login?> = dataStore.data.map { preferences ->
        gson.fromJson(preferences[USER_DATA] ?: "", Login::class.java)
    }

    suspend fun clear(){
        dataStore.edit {
            it.clear()
        }
    }

}