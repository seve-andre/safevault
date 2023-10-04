package com.mitch.safevault.core.data.repository

import com.mitch.safevault.core.util.SafeVaultLanguage
import com.mitch.safevault.core.util.SafeVaultTheme
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getTheme(): Flow<SafeVaultTheme>
    suspend fun setTheme(theme: SafeVaultTheme)

    fun getLanguage(): Flow<SafeVaultLanguage>
    fun setLanguage(language: SafeVaultLanguage)
}
