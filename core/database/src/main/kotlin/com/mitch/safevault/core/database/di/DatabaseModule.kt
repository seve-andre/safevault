package com.mitch.safevault.core.database.di

import android.content.Context
import androidx.room.Room
import com.mitch.safevault.core.database.SafeVaultRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesSafeVaultDatabase(@ApplicationContext appContext: Context): SafeVaultRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            SafeVaultRoomDatabase::class.java,
            "safevault.db"
        ).build()
    }
}
