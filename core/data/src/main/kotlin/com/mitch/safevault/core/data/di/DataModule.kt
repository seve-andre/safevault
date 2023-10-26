package com.mitch.safevault.core.data.di

import com.mitch.safevault.core.data.remote.AuthApi
import com.mitch.safevault.core.data.remote.DefaultAuthApi
import com.mitch.safevault.core.data.repository.DefaultAuthRepository
import com.mitch.safevault.core.data.repository.DefaultUserSettingsRepository
import com.mitch.safevault.core.data.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.safevault.core.data.util.network.NetworkMonitor
import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.domain.repository.UserSettingsRepository
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
    ): UserSettingsRepository

    @Binds
    abstract fun bindsAuthRepository(
        authRepository: DefaultAuthRepository
    ): AuthRepository

    @Binds
    abstract fun bindsAuthApi(
        authApi: DefaultAuthApi
    ): AuthApi
}
