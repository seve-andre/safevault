package com.mitch.safevault.core.data.repository

import com.mitch.safevault.core.data.datasource.LanguageLocalDataSource
import com.mitch.safevault.core.data.mapper.toAppLanguage
import com.mitch.safevault.core.data.mapper.toDataModel
import com.mitch.safevault.core.data.mapper.toDomainModel
import com.mitch.safevault.core.datastore.UserPreferencesLocalDataSource
import com.mitch.safevault.core.util.SafeVaultLanguage
import com.mitch.safevault.core.util.SafeVaultTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultUserSettingsRepository @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override fun getThemeStream(): Flow<SafeVaultTheme> {
        return userPreferencesLocalDataSource.getAppThemeStream().map { it.toDomainModel() }
    }

    override suspend fun setTheme(theme: SafeVaultTheme) {
        userPreferencesLocalDataSource.setAppTheme(theme.toDataModel())
    }

    override fun getLanguageStream(): Flow<SafeVaultLanguage> {
        return languageLocalDataSource.getLocale().map { it.toAppLanguage() }
    }

    override fun setLanguage(language: SafeVaultLanguage) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
