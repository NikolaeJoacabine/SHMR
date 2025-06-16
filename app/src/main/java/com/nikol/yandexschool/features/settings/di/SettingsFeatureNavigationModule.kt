package com.nikol.yandexschool.features.settings.di

import com.nikol.yandexschool.di.NavigationScope
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.settings.nav.SettingsFeatureApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet


@Module
class SettingsFeatureNavigationModule {

    @NavigationScope
    @IntoSet
    @Provides
    fun provideSettingsFeatureNavigationApi(): FeatureApi {
        return SettingsFeatureApi()
    }
}