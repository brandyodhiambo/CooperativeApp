package com.brandy.cooperativeapp.presentation.state

data class TextFieldState(
    val text: String = "",
    val error: String? = null,
    val isHintVisible: Boolean = false,
)