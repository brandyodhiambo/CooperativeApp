package com.brandy.cooperativeapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.brandy.cooperativeapp.R
import com.brandy.cooperativeapp.presentation.components.CommonButton
import com.brandy.cooperativeapp.presentation.components.CommonTextField
import com.brandy.cooperativeapp.presentation.event.UiEvents
import com.brandy.cooperativeapp.presentation.utils.ContentWithCircularIndicator
import com.brandy.cooperativeapp.presentation.utils.PasswordToggle
import com.brandy.cooperativeapp.ui.theme.CooperativeAppTheme
import com.brandy.cooperativeapp.ui.theme.GreenColor
import com.brandy.cooperativeapp.ui.theme.WhiteColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.NavigateEvent -> {
                    Toast.makeText(context, "Logged in successfully ", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    navController.navigate(event.route)
                }

                is UiEvents.SnackbarEvent -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = event.message,
                        )
                    }
                }
            }
        }
    }

    LoginScreenContent(
        snackBarHost = { SnackbarHost(hostState = snackBarHostState) },
        uiState = uiState,
        onEvent = { event ->
            viewModel.onAction(event)
        }
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    snackBarHost: @Composable () -> Unit = {}

) {
    Scaffold(
        snackbarHost = snackBarHost,
    ) { paddingValues ->
        val keyboardController = LocalSoftwareKeyboardController.current
        Box {
            Image(
                painter = painterResource(id = R.drawable.bgimage),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    LoginAccountHeader()
                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {
                    Text(
                        text = "Use your credentials to login",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                item {
                    CommonTextField(
                        value = uiState.usernameState.text,
                        onValueChange = {
                            onEvent(LoginUiEvent.OnUsernameChanged(it))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "",
                                tint = WhiteColor
                            )

                        },
                        placeholder = {
                            Text(
                                text = "Username",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.4f
                                    )
                                ),
                                color = WhiteColor
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    CommonTextField(
                        value = uiState.passwordState.text,
                        onValueChange = {
                            onEvent(LoginUiEvent.OnPasswordChange(it))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "",
                                tint = WhiteColor
                            )

                        },
                        placeholder = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.4f,
                                    ),
                                ),
                                color = WhiteColor
                            )
                        },
                        trailingIcon = {
                            PasswordToggle(
                                isHintVisible = uiState.passwordState.isHintVisible,
                                onClickToggle = {
                                    onEvent(LoginUiEvent.OnTogglePasswordVisibility)
                                },
                            )
                        },
                        visualTransformation = if (uiState.passwordState.isHintVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    CommonButton(
                        onClick = {
                            keyboardController?.hide()
                            onEvent(
                                LoginUiEvent.OnClickSignIn(
                                    username = uiState.usernameState.text,
                                    password = uiState.passwordState.text,
                                )
                            )
                        },
                        enabled = uiState.usernameState.text.isNotBlank() && uiState.passwordState.text.isNotBlank() && uiState.isLoading.not(),
                    ) {
                        ContentWithCircularIndicator(
                            isLoading = uiState.isLoading,
                            titleWhenLoading = "Logging in...",
                            titleWhenNotLoading = "Login",
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun LoginAccountHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
        )
        Text(
            text = "Welcome to Co-op Bank",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W500
            ),
            color = GreenColor
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    CooperativeAppTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
            onEvent = {},
        )
    }
}