package com.mitch.safevault.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.util.asResult
import com.mitch.safevault.core.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userSettingsRepository: com.mitch.safevault.core.data.repository.UserSettingsRepository
) : ViewModel() {

    val uiState = combine(
        userSettingsRepository.getLanguage(),
        userSettingsRepository.getTheme(),
        ::Pair
    ).asResult()
        .map { result ->
            when (result) {
                Result.Loading -> HomeUiState.Loading
                is Result.Success -> {
                    val (language, theme) = result.data

                    HomeUiState.Success(
                        language = language,
                        theme = theme
                    )
                }

                is Result.Error -> HomeUiState.Error()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    fun updateTheme(theme: com.mitch.safevault.core.util.SafeVaultTheme) {
        viewModelScope.launch {
            userSettingsRepository.setTheme(theme)
        }
    }

    fun updateLanguage(language: com.mitch.safevault.core.util.SafeVaultLanguage) {
        viewModelScope.launch {
            userSettingsRepository.setLanguage(language)
        }
    }
}
