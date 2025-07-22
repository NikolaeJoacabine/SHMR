package com.nikol.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferencesDataStore(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    companion object {
        private val THEME_KEY = stringPreferencesKey("current_theme")
    }

    // Сохранение выбранной темы
    suspend fun editTheme(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = themeMode.name
        }
    }

    // Получение текущей темы как Flow
    val themeFlow: Flow<ThemeMode> = dataStore.data
        .map { preferences ->
            val name = preferences[THEME_KEY]
            ThemeMode.entries.find { it.name == name } ?: ThemeMode.System
        }
}