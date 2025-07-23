package com.nikol.settings.screens

import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.settings.SettingsManager
import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.theme.ThemeMode
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {


    private val _themeMode = MutableStateFlow(ThemeMode.System)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _colorScheme = MutableStateFlow(AppColorScheme.Green)
    val colorScheme: StateFlow<AppColorScheme> = _colorScheme.asStateFlow()


    val vibrationEnabled =
        settingsManager.vibrationEnabled.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    @RequiresApi(Build.VERSION_CODES.Q)
    val vibrationEffect = settingsManager.vibrationEffect.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        VibrationEffect.EFFECT_CLICK
    )

    val locale = settingsManager.locale
        .stateIn(viewModelScope, SharingStarted.Eagerly, Locale.getDefault())

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

    fun setLocale(locale: Locale) = viewModelScope.launch {
        settingsManager.setLocale(locale.language)
    }

    fun setColorScheme(scheme: AppColorScheme) {
        viewModelScope.launch {
            settingsManager.setColorScheme(scheme)
        }
    }

    fun setVibrationEnabled(enabled: Boolean) = viewModelScope.launch {
        settingsManager.setVibrationEnabled(enabled)
    }

    fun setVibrationEffect(effect: Int) = viewModelScope.launch {
        settingsManager.setVibrationEffect(effect)
    }

    val appVersion = settingsManager.appVersion
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    fun updateAppVersion(version: String) {
        viewModelScope.launch {
            settingsManager.setAppVersion(version)
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