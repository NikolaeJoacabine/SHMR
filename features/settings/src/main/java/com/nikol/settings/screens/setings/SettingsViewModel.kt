package com.nikol.settings.screens.setings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.settings.SettingsManager
import com.nikol.settings.ThemeMode
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

    init {
        viewModelScope.launch {
            settingsManager.themeMode
                .collectLatest { theme ->
                    _themeMode.value = theme
                }
        }
    }

    fun setTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            settingsManager.setThemeMode(themeMode)
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