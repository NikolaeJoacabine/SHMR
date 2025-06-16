package com.nikol.yandexschool.features.settings.di

import android.content.Context
import com.nikol.yandexschool.di.AppComponent
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [SettingsFeatureModule::class])
@SettingsComponentScope
interface SettingsFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            appComponent: AppComponent
        ): SettingsFeatureComponent
    }
}