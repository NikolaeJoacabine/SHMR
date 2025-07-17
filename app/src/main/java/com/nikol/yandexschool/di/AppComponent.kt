package com.nikol.yandexschool.di

import android.content.Context
import com.nikol.di.FeatureDependencies
import com.nikol.yandexschool.ui.splash.SplashScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent : FeatureDependencies {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun splashScreenViewModelFactory(): SplashScreenViewModel.Factory.Factory
}
