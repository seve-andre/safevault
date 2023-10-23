package com.mitch.safevault.core.domain.repository

import com.mitch.safevault.core.util.SafeVaultLanguage
import com.mitch.safevault.core.util.SafeVaultTheme
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getThemeStream(): Flow<SafeVaultTheme>
    suspend fun setTheme(theme: SafeVaultTheme)

    fun getLanguageStream(): Flow<SafeVaultLanguage>
    fun setLanguage(language: SafeVaultLanguage)
}
