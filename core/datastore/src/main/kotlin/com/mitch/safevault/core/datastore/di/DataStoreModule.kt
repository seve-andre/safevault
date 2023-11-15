package com.mitch.safevault.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.mitch.safevault.core.datastore.ProtoUserFlow
import com.mitch.safevault.core.datastore.ProtoUserFlowSerializer
import com.mitch.safevault.core.datastore.ProtoUserPreferences
import com.mitch.safevault.core.datastore.ProtoUserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        protoUserPreferencesSerializer: ProtoUserPreferencesSerializer
    ): DataStore<ProtoUserPreferences> =
        DataStoreFactory.create(
            serializer = protoUserPreferencesSerializer,
            scope = CoroutineScope(SupervisorJob())
        ) {
            context.dataStoreFile("user_preferences.pb")
        }

    @Provides
    @Singleton
    fun providesUserFlowDataStore(
        @ApplicationContext context: Context,
        protoUserFlowSerializer: ProtoUserFlowSerializer
    ): DataStore<ProtoUserFlow> =
        DataStoreFactory.create(
            serializer = protoUserFlowSerializer,
            scope = CoroutineScope(SupervisorJob())
        ) {
            context.dataStoreFile("user_flow.pb")
        }
}
