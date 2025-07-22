package com.nikol.settings

import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    val themeMode: Flow<ThemeMode>
    val colorScheme: Flow<AppColorScheme>

    suspend fun setThemeMode(themeMode: ThemeMode)
    suspend fun setColorScheme(colorScheme: AppColorScheme)
}