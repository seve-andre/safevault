package com.mitch.safevault.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserFlowLocalDataSource @Inject constructor(
    private val userFlowDataStore: DataStore<ProtoUserFlow>
) {
    suspend fun logUserIn() {
        userFlowDataStore.updateData {
            it.toBuilder().setIsUserLoggedIn(true).build()
        }
    }

    suspend fun logUserOut() {
        userFlowDataStore.updateData {
            it.toBuilder().clearIsUserLoggedIn().build()
        }
    }

    fun isUserLoggedInStream(): Flow<Boolean> = userFlowDataStore.data.map { it.isUserLoggedIn }
}
