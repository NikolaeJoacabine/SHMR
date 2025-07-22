package com.nikol.settings.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferencesDataStore(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    companion object {
        private val THEME_KEY = stringPreferencesKey("current_theme")
        private val COLOR_SCHEME_KEY = stringPreferencesKey("color_scheme")
    }

    suspend fun editTheme(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = themeMode.name
        }
    }

    val themeFlow: Flow<ThemeMode> = dataStore.data
        .map { preferences ->
            ThemeMode.entries.find { it.name == preferences[THEME_KEY] } ?: ThemeMode.System
        }

    suspend fun editColorScheme(colorScheme: AppColorScheme) {
        dataStore.edit { preferences ->
            preferences[COLOR_SCHEME_KEY] = colorScheme.name
        }
    }

    val colorSchemeFlow: Flow<AppColorScheme> = dataStore.data
        .map { preferences ->
            AppColorScheme.fromName(preferences[COLOR_SCHEME_KEY])
        }
}
