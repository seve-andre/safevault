package com.mitch.safevault.feature.auth.login

sealed interface LogInUiState {
    data object Loading : LogInUiState
    data class Success(val messageId: Int) : LogInUiState
    data class Error(val messageId: Int) : LogInUiState
}
