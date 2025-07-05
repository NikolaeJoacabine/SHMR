package com.nikol.settings.di

import com.nikol.di.FeatureDependencies
import dagger.Component

@Component(
    dependencies = [FeatureDependencies::class]
)
@SettingsFeatureScope
interface SettingsFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: FeatureDependencies
        ): SettingsFeatureComponent
    }
}
