package com.brandy.cooperativeapp.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brandy.cooperativeapp.ui.theme.CooperativeAppTheme
import com.brandy.cooperativeapp.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    readOnly:Boolean = false,
    enabled:Boolean = true,
    errorText: String = "",
    onClick: () -> Unit = { },
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = WhiteColor,
        unfocusedBorderColor = WhiteColor,
        cursorColor = WhiteColor,
        focusedLabelColor = WhiteColor,
        unfocusedLabelColor = WhiteColor,
        focusedTextColor = WhiteColor,
        unfocusedTextColor = WhiteColor
    ),
) {
    Column {
        if (label != null) {
            label()
            Spacer(modifier = Modifier.height(8.dp))
        }
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp),
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            colors = colors,
            enabled = enabled,
            readOnly = readOnly,
            shape = shape,
            placeholder = placeholder,
            visualTransformation = visualTransformation,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onClick()
                            }
                        }
                    }
                },
        )
        if (isError) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = errorText,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Composable
fun CommonPhoneTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorText: String = "",
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(
            alpha = 0.2f,
        ),
        focusedIndicatorColor = MaterialTheme.colorScheme.primary
            .copy(alpha = 0.2f),
        unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(.01f),
        focusedContainerColor = MaterialTheme.colorScheme.primary.copy(.05f),
    ),
) {
    Column {
        if (label != null) {
            label()
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (isError) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = errorText,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonTextFieldsPreview() {
    CooperativeAppTheme {
        Column {
            CommonTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                label = {
                    Text(
                        text = "Name",
                    )
                },
                value = "John Doe",
                onValueChange = {},
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.3f,
                    ),
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary.copy(.1f),
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonPhoneTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                label = {
                    Text(
                        text = "Phone Number",
                    )
                },
                value = "071234f678",
                onValueChange = {},
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.3f,
                    ),
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary.copy(.1f),
                ),
            )
        }
    }
}
