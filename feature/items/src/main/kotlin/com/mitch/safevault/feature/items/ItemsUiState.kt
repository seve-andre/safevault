package com.mitch.safevault.feature.items

sealed interface ItemsUiState {
    data object Loading : ItemsUiState

    data class Error(
        val error: String? = null
    ) : ItemsUiState

    data object Success : ItemsUiState
}
