package com.brandy.cooperativeapp.presentation.auth

import com.brandy.cooperativeapp.presentation.state.TextFieldState

data class LoginUiState(

    val usernameState: TextFieldState = TextFieldState(),
    val passwordState: TextFieldState = TextFieldState(),
    val isLoading:Boolean = false,
)