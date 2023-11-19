package com.mitch.safevault.feature.auth.signup

import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError

sealed interface SignUpUiState {
    data object Idle : SignUpUiState
    data object Loading : SignUpUiState
    data object Success : SignUpUiState
    data class AuthenticationFailed(
        val emailAuthError: EmailError.Auth? = null,
        val passwordAuthError: PasswordError.Auth? = null
    ) : SignUpUiState
}
