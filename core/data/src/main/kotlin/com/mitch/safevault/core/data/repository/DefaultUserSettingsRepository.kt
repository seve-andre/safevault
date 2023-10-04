package com.mitch.safevault.core.data.repository

import com.mitch.safevault.core.data.datasource.LanguageLocalDataSource
import com.mitch.safevault.core.datastore.UserPreferencesLocalDataSource
import com.mitch.safevault.core.data.mapper.toAppLanguage
import com.mitch.safevault.core.data.mapper.toDomainModel
import com.mitch.safevault.core.data.mapper.toProtoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultUserSettingsRepository @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override fun getTheme(): Flow<com.mitch.safevault.core.util.SafeVaultTheme> {
        return userPreferencesLocalDataSource.getProtoTheme().map { it.toDomainModel() }
    }

    override suspend fun setTheme(theme: com.mitch.safevault.core.util.SafeVaultTheme) {
        userPreferencesLocalDataSource.setProtoTheme(theme.toProtoModel())
    }

    override fun getLanguage(): Flow<com.mitch.safevault.core.util.SafeVaultLanguage> {
        return languageLocalDataSource.getLocale().map { it.toAppLanguage() }
    }

    override fun setLanguage(language: com.mitch.safevault.core.util.SafeVaultLanguage) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
