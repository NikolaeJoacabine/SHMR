package com.nikol.settings

import kotlinx.coroutines.flow.Flow

interface SettingsManager {

    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(themeMode: ThemeMode)

}