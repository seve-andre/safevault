package com.mitch.safevault.feature.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.domain.usecase.LogInUseCase
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.password.PasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    val emailState by mutableStateOf(
        EmailState(onValidateEmail = logInUseCase::validateEmail)
    )

    val passwordState by mutableStateOf(
        PasswordState(onValidatePassword = logInUseCase::validatePassword)
    )

    private val _logInUiState = MutableStateFlow<LogInUiState>(LogInUiState.Idle)
    val logInUiState = _logInUiState.asStateFlow()

    fun logIn() {
        if (!emailState.shouldStartValidation) {
            emailState.startValidation()
        }

        if (!passwordState.shouldStartValidation) {
            passwordState.startValidation()
        }

        if (emailState.hasError() || passwordState.hasError()) {
            return
        }

        viewModelScope.launch {
            _logInUiState.value = LogInUiState.Loading
            when (val result = logInUseCase.logIn(emailState.email, passwordState.password)) {
                is LogInResult.Error -> _logInUiState.value = LogInUiState.AuthenticationFailed(
                    emailAuthError = result.emailError,
                    passwordAuthError = result.passwordError
                )

                LogInResult.Success -> _logInUiState.value = LogInUiState.Success
            }
        }
    }
}
