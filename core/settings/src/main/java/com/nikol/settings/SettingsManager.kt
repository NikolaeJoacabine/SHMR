package com.nikol.settings

import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface SettingsManager {
    val themeMode: Flow<ThemeMode>
    val colorScheme: Flow<AppColorScheme>
    val vibrationEnabled: Flow<Boolean>
    val vibrationEffect: Flow<Int>
    val locale: Flow<Locale>
    val appVersion: Flow<String>
    val syncInterval: Flow<Int>

    suspend fun setThemeMode(themeMode: ThemeMode)
    suspend fun setColorScheme(colorScheme: AppColorScheme)
    suspend fun setVibrationEnabled(enabled: Boolean)
    suspend fun setVibrationEffect(effect: Int)
    suspend fun setLocale(locale: String)
    suspend fun setAppVersion(version: String)
    suspend fun setIntervalWorkManager(newHours: Int)
}
