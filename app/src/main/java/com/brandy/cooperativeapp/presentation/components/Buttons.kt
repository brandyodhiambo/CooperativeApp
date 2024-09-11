package com.brandy.cooperativeapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brandy.cooperativeapp.ui.theme.CooperativeAppTheme
import com.brandy.cooperativeapp.ui.theme.GreenColor
import com.brandy.cooperativeapp.ui.theme.WhiteColor

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = WhiteColor,
        containerColor = GreenColor,
        disabledContainerColor = GreenColor.copy(alpha = 0.40f),
        disabledContentColor = WhiteColor.copy(alpha = 0.40f)
    ),
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    content: @Composable () -> Unit = {},
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        shape = shape,
    ) {
        content()
    }
}

@Composable
fun CommonTextButton() {
}

@Composable
fun CommonIconButton() {
}

@Preview(showBackground = true)
@Composable
fun ButtonPreviews() {
    CooperativeAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            CommonButton(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Click Me")
            }
        }
    }
}
