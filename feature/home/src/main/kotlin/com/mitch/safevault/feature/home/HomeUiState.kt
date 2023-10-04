package com.mitch.safevault.feature.home

import com.mitch.safevault.core.util.SafeVaultLanguage
import com.mitch.safevault.core.util.SafeVaultTheme

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data class Success(
        val language: SafeVaultLanguage,
        val theme: SafeVaultTheme
    ) : HomeUiState
}
