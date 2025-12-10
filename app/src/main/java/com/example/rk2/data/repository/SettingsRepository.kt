package com.example.rk2.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.rk2.domain.model.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Repository for managing app settings using DataStore.
 */
@Singleton
class SettingsRepository @Inject constructor(
    private val context: Context
) {
    private val themeKey = stringPreferencesKey("app_theme")
    
    /**
     * Get the current theme preference.
     * Defaults to SYSTEM if not set.
     */
    fun getTheme(): Flow<AppTheme> {
        return context.dataStore.data.map { preferences ->
            val themeString = preferences[themeKey] ?: AppTheme.SYSTEM.name
            try {
                AppTheme.valueOf(themeString)
            } catch (e: IllegalArgumentException) {
                AppTheme.SYSTEM
            }
        }
    }
    
    /**
     * Set the theme preference.
     */
    suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }
}

