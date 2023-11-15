package com.mitch.safevault.core.datastore

import androidx.datastore.core.DataStore
import com.mitch.safevault.core.datastore.ProtoUserPreferences.ProtoAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * [UserPreferencesLocalDataSource] is the mediator between [ProtoUserPreferences] Datastore and
 * the repo to exchange data from the Datastore file
 *
 * @property userPreferencesDataStore is the actual [ProtoUserPreferences] Datastore
 */
class UserPreferencesLocalDataSource @Inject constructor(
    private val userPreferencesDataStore: DataStore<ProtoUserPreferences>
) {
    suspend fun setAppTheme(theme: ProtoAppTheme) {
        userPreferencesDataStore.updateData {
            it.toBuilder().setTheme(theme).build()
        }
    }

    fun getAppThemeStream(): Flow<ProtoAppTheme> = userPreferencesDataStore.data.map { it.theme }
}
