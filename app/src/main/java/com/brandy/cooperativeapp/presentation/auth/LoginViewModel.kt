package com.brandy.cooperativeapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandy.cooperativeapp.data.network.request.LoginRequest
import com.brandy.cooperativeapp.data.utils.NetworkResult
import com.brandy.cooperativeapp.domain.repository.AuthRepository
import com.brandy.cooperativeapp.navigation.Route
import com.brandy.cooperativeapp.presentation.event.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventFlow = Channel<UiEvents>(Channel.UNLIMITED)
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private fun setUserNameState(value: String, error: String? = null) {
        _uiState.update {
            it.copy(
                usernameState = it.usernameState.copy(
                    text = value,
                    error = error,
                )
            )
        }
    }

    private fun setPasswordState(value: String, error: String? = null) {
        _uiState.update {
            it.copy(
                passwordState = it.passwordState.copy(
                    text = value,
                    error = error,
                ),
            )
        }
    }

    private fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(
                passwordState = it.passwordState.copy(
                    isHintVisible = it.passwordState.isHintVisible.not(),
                ),
            )
        }
    }

    fun onAction(action: LoginUiEvent) {
        when (action) {
            is LoginUiEvent.OnUsernameChanged -> {
                setUserNameState(action.username)
            }

            is LoginUiEvent.OnPasswordChange -> {
                setPasswordState(action.password)
            }

            is LoginUiEvent.OnTogglePasswordVisibility -> {
                togglePasswordVisibility()
            }

            is LoginUiEvent.OnClickSignIn -> {
                login(
                    username = action.username,
                    password = action.password
                )
            }
        }
    }

    private fun login(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }


            val loginRequest = LoginRequest(
                username = username.trim(),
                password = password.trim()
            )

            when (val loginResult = authRepository.login(
                loginRequest = loginRequest
            )) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _eventFlow.trySend(
                        UiEvents.NavigateEvent(
                            Route.HomeUi.route
                        )
                    )
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _eventFlow.trySend(
                        UiEvents.SnackbarEvent(
                            loginResult.message ?: "Unknown error occurred!"
                        )
                    )
                }
            }
        }
    }
}