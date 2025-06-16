package com.nikol.yandexschool.di

import android.content.Context
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.account.di.AccountFeatureNavigationModule
import com.nikol.yandexschool.features.articles.di.ArticlesFeatureNavigationModule
import com.nikol.yandexschool.features.articles.screnns.articles.ArticlesScreenViewModelFactoryFactory
import com.nikol.yandexschool.features.settings.di.SettingsFeatureNavigationModule
import com.nikol.yandexschool.features.transaction.di.TransactionFeatureNavigationModule
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [TransactionFeatureNavigationModule::class, AccountFeatureNavigationModule::class, ArticlesFeatureNavigationModule::class, SettingsFeatureNavigationModule::class]
)
@NavigationScope
interface NavigationComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            appComponent: AppComponent
        ): NavigationComponent
    }

    val featureNavigationApis: Set<@JvmSuppressWildcards FeatureApi>
}