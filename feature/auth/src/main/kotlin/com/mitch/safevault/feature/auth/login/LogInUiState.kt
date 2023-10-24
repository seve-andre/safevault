package com.mitch.safevault.feature.auth.login

sealed interface LogInUiState {
    data object Idle : LogInUiState
    data object Loading : LogInUiState
    data object Success : LogInUiState
    data object Error : LogInUiState
}
