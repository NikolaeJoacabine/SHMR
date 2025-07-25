package com.nikol.yandexschool.di

import com.nikol.settings.SettingsManager
import com.nikol.settings.SettingsManagerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SettingsModule {
    @Binds
    @Singleton
    fun bindSettingsPreferencesDataStore(impl: SettingsManagerImpl): SettingsManager
}