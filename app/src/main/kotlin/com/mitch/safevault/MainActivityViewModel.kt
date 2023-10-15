package com.mitch.safevault

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.data.repository.UserSettingsRepository
import com.mitch.safevault.core.util.SafeVaultTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    /**
     * Initial [MainActivity] ui state is set to [MainActivityUiState.Loading] and mapped to
     * [MainActivityUiState.Success] once the [SafeVaultTheme] data is retrieved
     */
    val uiState = userSettingsRepository.getThemeStream().map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainActivityUiState.Loading
    )
}

sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()
    data class Success(val theme: SafeVaultTheme) : MainActivityUiState()
}
