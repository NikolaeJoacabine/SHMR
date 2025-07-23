package com.nikol.settings

import android.os.Build
import androidx.annotation.RequiresApi
import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.dataStore.SettingsPreferencesDataStore
import com.nikol.settings.theme.ThemeMode
import javax.inject.Inject


class SettingsManagerImpl @Inject constructor(
    private val settingsPreferencesDataStore: SettingsPreferencesDataStore
) : SettingsManager {

    override val themeMode = settingsPreferencesDataStore.themeFlow

    override val colorScheme = settingsPreferencesDataStore.colorSchemeFlow

    override val vibrationEnabled = settingsPreferencesDataStore.vibrationEnabledFlow

    @RequiresApi(Build.VERSION_CODES.Q)
    override val vibrationEffect = settingsPreferencesDataStore.vibrationEffectFlow

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        settingsPreferencesDataStore.editTheme(themeMode)
    }

    override suspend fun setColorScheme(colorScheme: AppColorScheme) {
        settingsPreferencesDataStore.editColorScheme(colorScheme)
    }

    override suspend fun setVibrationEnabled(enabled: Boolean) {
        settingsPreferencesDataStore.setVibrationEnabled(enabled)
    }

    override suspend fun setVibrationEffect(effect: Int) {
        settingsPreferencesDataStore.setVibrationEffect(effect)
    }
}
