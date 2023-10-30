package com.mitch.safevault.feature.auth.login

import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError

sealed interface LogInUiState {
    data object Idle : LogInUiState
    data object Loading : LogInUiState
    data object Success : LogInUiState
    data class AuthenticationFailed(
        val emailAuthError: EmailError.Auth? = null,
        val passwordAuthError: PasswordError.Auth? = null
    ) : LogInUiState
}
