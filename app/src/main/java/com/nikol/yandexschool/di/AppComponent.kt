package com.nikol.yandexschool.di

import android.content.Context
import com.nikol.di.FeatureDependencies
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
}
