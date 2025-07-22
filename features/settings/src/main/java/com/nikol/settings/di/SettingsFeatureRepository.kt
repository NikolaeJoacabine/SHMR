package com.nikol.settings.di

import com.nikol.settings.SettingsManager
import com.nikol.settings.SettingsManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface SettingsFeatureRepository {

    @Binds
    @SettingsFeatureScope
    fun bindSettingsRepository(impl: SettingsManagerImpl): SettingsManager

}
