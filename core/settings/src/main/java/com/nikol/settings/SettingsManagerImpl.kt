package com.nikol.settings

import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.dataStore.SettingsPreferencesDataStore
import com.nikol.settings.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SettingsManagerImpl @Inject constructor(
    private val settingsPreferencesDataStore: SettingsPreferencesDataStore
) : SettingsManager {
    override val themeMode: Flow<ThemeMode> = settingsPreferencesDataStore.themeFlow
    override val colorScheme: Flow<AppColorScheme> = settingsPreferencesDataStore.colorSchemeFlow

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        settingsPreferencesDataStore.editTheme(themeMode)
    }

    override suspend fun setColorScheme(colorScheme: AppColorScheme) {
        settingsPreferencesDataStore.editColorScheme(colorScheme)
    }
}