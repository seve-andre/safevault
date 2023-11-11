package com.mitch.safevault.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserFlowDataStore(
    private val context: Context
) {
    companion object {
        val Context.userFlowDataStore: DataStore<Preferences> by preferencesDataStore(name = "userFlowDataStore")
        val IS_USER_LOGGED_IN = booleanPreferencesKey("isUserLoggedIn")
    }

    val userLoggedInFlow = context.userFlowDataStore.data.map { it[IS_USER_LOGGED_IN] ?: false }

    suspend fun logUserIn() {
        context.userFlowDataStore.edit {
            it[IS_USER_LOGGED_IN] = true
        }
    }

    suspend fun logUserOut() {
        context.userFlowDataStore.edit {
            it[IS_USER_LOGGED_IN] = false
        }
    }
}
