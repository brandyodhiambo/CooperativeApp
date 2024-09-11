package com.brandy.cooperativeapp.presentation.utils

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.brandy.cooperativeapp.R
import com.brandy.cooperativeapp.ui.theme.WhiteColor


@Composable
fun PasswordToggle(
    isHintVisible: Boolean,
    onClickToggle: () -> Unit,
    tint: Color = WhiteColor,
) {
    IconButton(onClick = onClickToggle) {
        Icon(
            painter = if (isHintVisible) {
                painterResource(R.drawable.ic_visibility)
            } else {
                painterResource(R.drawable.ic_visibility_off)
            },
            contentDescription = null,
            tint = tint,
        )
    }
}