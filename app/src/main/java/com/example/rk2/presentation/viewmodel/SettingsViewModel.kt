package com.example.rk2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rk2.data.repository.SettingsRepository
import com.example.rk2.domain.model.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing app settings.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    
    private val _theme = MutableStateFlow<AppTheme>(AppTheme.SYSTEM)
    val theme: StateFlow<AppTheme> = _theme.asStateFlow()
    
    init {
        loadTheme()
    }
    
    /**
     * Load the theme preference from the repository.
     */
    private fun loadTheme() {
        viewModelScope.launch {
            settingsRepository.getTheme().collect { theme ->
                _theme.value = theme
            }
        }
    }
    
    /**
     * Update the theme preference.
     */
    fun updateTheme(theme: AppTheme) {
        viewModelScope.launch {
            settingsRepository.setTheme(theme)
        }
    }
}

