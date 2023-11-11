package com.mitch.safevault.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserAppFlowManager(
    private val context: Context
) {
    companion object {
        val Context.userAppFlowDataStore: DataStore<Preferences> by preferencesDataStore(name = "userAppFlowDataStore")
        val IS_USER_LOGGED_IN = booleanPreferencesKey("isUserLoggedIn")
    }

    val userLoggedInFlow = context.userAppFlowDataStore.data.map { it[IS_USER_LOGGED_IN] ?: false }

    suspend fun logUserIn() {
        context.userAppFlowDataStore.edit {
            it[IS_USER_LOGGED_IN] = true
        }
    }

    suspend fun logUserOut() {
        context.userAppFlowDataStore.edit {
            it[IS_USER_LOGGED_IN] = false
        }
    }
}
