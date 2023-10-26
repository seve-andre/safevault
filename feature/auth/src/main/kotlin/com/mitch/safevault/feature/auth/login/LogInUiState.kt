package com.mitch.safevault.feature.auth.login

import com.mitch.safevault.core.util.validator.email.EmailAuthError
import com.mitch.safevault.core.util.validator.password.PasswordAuthError

sealed interface LogInUiState {
    data object Idle : LogInUiState
    data object Loading : LogInUiState
    data object Success : LogInUiState
    data class AuthenticationFailed(
        val emailAuthError: EmailAuthError? = null,
        val passwordAuthError: PasswordAuthError? = null
    ) : LogInUiState
}
