package com.nikol.settings.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.settings.SettingsManager
import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.theme.ThemeMode
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {


    private val _themeMode = MutableStateFlow(ThemeMode.System)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _colorScheme = MutableStateFlow(AppColorScheme.Green)
    val colorScheme: StateFlow<AppColorScheme> = _colorScheme.asStateFlow()

    init {
        viewModelScope.launch {
            settingsManager.themeMode.collectLatest { _themeMode.value = it }
        }
        viewModelScope.launch {
            settingsManager.colorScheme.collectLatest { _colorScheme.value = it }
        }
    }

    fun setTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            settingsManager.setThemeMode(themeMode)
        }
    }

    fun setColorScheme(scheme: AppColorScheme) {
        viewModelScope.launch {
            settingsManager.setColorScheme(scheme)
        }
    }

    class Factory @AssistedInject constructor(
        private val settingsManager: SettingsManager
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(settingsManager) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(): SettingsViewModel.Factory
        }
    }
}