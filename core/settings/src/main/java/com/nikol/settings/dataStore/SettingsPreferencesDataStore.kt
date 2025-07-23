package com.nikol.settings.dataStore

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nikol.settings.color.AppColorScheme
import com.nikol.settings.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale


class SettingsPreferencesDataStore(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    companion object {
        private val THEME_KEY = stringPreferencesKey("current_theme")
        private val COLOR_SCHEME_KEY = stringPreferencesKey("color_scheme")
        private val VIBRATION_ENABLED_KEY = booleanPreferencesKey("vibration_enabled")
        private val VIBRATION_EFFECT_KEY = intPreferencesKey("vibration_effect")
        private val LOCALE_KEY = stringPreferencesKey("selected_locale")
        private val APP_VERSION_KEY = stringPreferencesKey("app_version")
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

    suspend fun setVibrationEnabled(enabled: Boolean) {
        dataStore.edit { it[VIBRATION_ENABLED_KEY] = enabled }
    }

    suspend fun setVibrationEffect(effect: Int) {
        dataStore.edit { it[VIBRATION_EFFECT_KEY] = effect }
    }

    val vibrationEnabledFlow: Flow<Boolean> = dataStore.data
        .map { it[VIBRATION_ENABLED_KEY] ?: true }

    @RequiresApi(Build.VERSION_CODES.Q)
    val vibrationEffectFlow: Flow<Int> = dataStore.data
        .map { it[VIBRATION_EFFECT_KEY] ?: VibrationEffect.EFFECT_CLICK }

    suspend fun setLocale(locale: Locale) {
        dataStore.edit { prefs ->
            prefs[LOCALE_KEY] = locale.toLanguageTag()
        }
    }

    val localeFlow: Flow<Locale> = dataStore.data
        .map { preferences ->
            val languageTag = preferences[LOCALE_KEY] ?: Locale.getDefault().toLanguageTag()
            Locale.forLanguageTag(languageTag)
        }

    suspend fun setAppVersion(version: String) {
        dataStore.edit { prefs -> prefs[APP_VERSION_KEY] = version }
    }

    val appVersionFlow: Flow<String> = dataStore.data
        .map { prefs -> prefs[APP_VERSION_KEY] ?: "" }
}
