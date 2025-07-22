package com.nikol.settings.di

import com.nikol.settings.SettingsManager
import com.nikol.di.FeatureDependencies
import com.nikol.settings.screens.SettingsViewModel
import dagger.Component

@Component(
    dependencies = [FeatureDependencies::class],
    modules = [SettingsFeatureRepository::class]
)
@SettingsFeatureScope
interface SettingsFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: FeatureDependencies
        ): SettingsFeatureComponent
    }

    fun settingsManager(): SettingsManager

    fun viewModelFactory(): SettingsViewModel.Factory.Factory
}
