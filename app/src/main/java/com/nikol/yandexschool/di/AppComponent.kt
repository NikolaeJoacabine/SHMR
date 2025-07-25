package com.nikol.yandexschool.di

import android.content.Context
import com.nikol.di.FeatureDependencies
import com.nikol.settings.screens.SettingsViewModel
import com.nikol.settings.screens.pincode.PinCodeViewModel
import com.nikol.yandexschool.ui.splash.SplashScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent : FeatureDependencies {

    fun inject(app: App)
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun splashScreenViewModelFactory(): SplashScreenViewModel.Factory.Factory

    fun settingsFeatureComponentFactory(): SettingsViewModel.Factory.Factory

    fun settingsPinCodeFactory(): PinCodeViewModel.Factory
}
