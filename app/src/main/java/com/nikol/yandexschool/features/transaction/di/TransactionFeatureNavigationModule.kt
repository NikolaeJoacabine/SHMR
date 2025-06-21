package com.nikol.yandexschool.features.transaction.di

import com.nikol.yandexschool.di.NavigationScope
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.transaction.nav.TransactionFeatureApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class TransactionFeatureNavigationModule {

    @NavigationScope
    @Provides
    @IntoSet
    fun provideTransactionFeatureNavigationApi(): FeatureApi =
        TransactionFeatureApi()
}