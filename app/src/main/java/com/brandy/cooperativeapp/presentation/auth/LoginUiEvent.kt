package com.brandy.cooperativeapp.presentation.auth

sealed interface LoginUiEvent{
    data class OnUsernameChanged(val username:String): LoginUiEvent
    data class OnPasswordChange(val password:String): LoginUiEvent
    data class OnClickSignIn(
        val username: String,
        val password: String,
    ): LoginUiEvent

    data object OnTogglePasswordVisibility: LoginUiEvent
}