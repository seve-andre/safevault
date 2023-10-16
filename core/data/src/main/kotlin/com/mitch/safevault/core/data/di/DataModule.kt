package com.mitch.safevault.core.data.di

import com.mitch.safevault.core.data.repository.DefaultUserSettingsRepository
import com.mitch.safevault.core.domain.repository.UserSettingsRepository
import com.mitch.safevault.core.data.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.safevault.core.data.util.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    abstract fun bindsUserSettingsRepository(
        userSettingsRepository: DefaultUserSettingsRepository
    ): com.mitch.safevault.core.domain.repository.UserSettingsRepository
}
