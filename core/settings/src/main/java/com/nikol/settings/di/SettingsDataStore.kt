package com.nikol.settings.di

import android.content.Context
import com.nikol.settings.dataStore.SettingsPreferencesDataStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsDataStore {

    @Provides
    @Singleton
    fun provideSettingsPreferencesDataStore(context: Context): SettingsPreferencesDataStore {
        return SettingsPreferencesDataStore(context)
    }
}
