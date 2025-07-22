package com.nikol.settings.screens.setings.di

import com.nikol.settings.di.SettingsFeatureComponent
import com.nikol.settings.screens.setings.SettingsViewModel
import dagger.Component

@Component(
    dependencies = [SettingsFeatureComponent::class],
)
@SettingsScreenScope
interface SettingsScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            settingsFeatureComponent: SettingsFeatureComponent
        ): SettingsScreenComponent
    }

    fun viewModelFactory(): SettingsViewModel.Factory.Factory
}