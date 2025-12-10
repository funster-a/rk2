package com.example.rk2.di

import android.content.Context
import com.example.rk2.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing Settings-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    
    /**
     * Provides the SettingsRepository instance.
     */
    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository {
        return SettingsRepository(context)
    }
}

