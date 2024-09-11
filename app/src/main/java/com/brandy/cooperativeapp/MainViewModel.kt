package com.brandy.cooperativeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandy.cooperativeapp.data.local.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preference: Preference
) : ViewModel() {


    val loginState = preference.getAccessToken
        .map {
            LoginState.Success(it != null)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LoginState.Loading
        )
}

sealed interface LoginState{
    data object Loading:LoginState
    data class Success(val result:Boolean):LoginState
}