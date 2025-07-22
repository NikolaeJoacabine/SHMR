package com.nikol.settings

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    private val settingsPreferencesDataStore: SettingsPreferencesDataStore
) : SettingsManager {

    override val themeMode: Flow<ThemeMode>
        get() = settingsPreferencesDataStore.themeFlow

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        settingsPreferencesDataStore.editTheme(themeMode)
    }
}