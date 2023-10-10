package com.mitch.safevault.core.datastore

import androidx.datastore.core.DataStore
import com.mitch.safevault.core.datastore.ProtoUserPreferences.ProtoAppTheme
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * [UserPreferencesLocalDataSource] is the mediator between [ProtoUserPreferences] Datastore and
 * the repo to exchange data from the Datastore file
 *
 * @property userPreferences is the actual [ProtoUserPreferences] Datastore
 */
class UserPreferencesLocalDataSource @Inject constructor(
    private val userPreferences: DataStore<ProtoUserPreferences>
) {
    suspend fun setAppTheme(theme: ProtoAppTheme) {
        userPreferences.updateData {
            it.toBuilder().setTheme(theme).build()
        }
    }

    fun getAppThemeStream(): Flow<ProtoAppTheme> = userPreferences.data.map { it.theme }
}
