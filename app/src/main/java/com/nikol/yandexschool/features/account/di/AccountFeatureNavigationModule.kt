package com.nikol.yandexschool.features.account.di

import com.nikol.yandexschool.di.NavigationScope
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.account.nav.AccountFeatureApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class AccountFeatureNavigationModule {

    @NavigationScope
    @Provides
    @IntoSet
    fun provideAccountFeatureNavigationApi(): FeatureApi {
        return AccountFeatureApi()
    }
}