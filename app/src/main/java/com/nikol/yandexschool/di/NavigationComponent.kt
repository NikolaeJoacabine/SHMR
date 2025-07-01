package com.nikol.yandexschool.di

import com.nikol.account.nav.AccountFeatureImpl
import com.nikol.articles.nav.ArticlesFeatureImpl
import com.nikol.navigation.FeatureApi
import com.nikol.settings.nav.SettingsFeatureImpl
import com.nikol.transaction.nav.TransactionFeatureImpl
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Component(
    dependencies = [AppComponent::class],
    modules = [
        TransactionFeatureNavigationModule::class,
        AccountFeatureNavigationModule::class,
        ArticlesFeatureNavigationModule::class,
        SettingsFeatureNavigationModule::class
    ]
)
@NavigationScope
interface NavigationComponent {
    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent
        ): NavigationComponent
    }

    val featureNavigationApis: Set<@JvmSuppressWildcards FeatureApi>
}

@Module
object TransactionFeatureNavigationModule {
    @Provides
    @IntoSet
    @NavigationScope
    fun provideTransactionFeatureImpl(
        appComponent: AppComponent
    ): FeatureApi {
        return TransactionFeatureImpl(appComponent)
    }
}

@Module
object AccountFeatureNavigationModule {
    @Provides
    @IntoSet
    @NavigationScope
    fun provideTransactionFeatureImpl(
        appComponent: AppComponent
    ): FeatureApi {
        return AccountFeatureImpl(appComponent)
    }
}

@Module
object ArticlesFeatureNavigationModule {
    @Provides
    @IntoSet
    @NavigationScope
    fun provideTransactionFeatureImpl(
        appComponent: AppComponent
    ): FeatureApi {
        return ArticlesFeatureImpl(appComponent)
    }
}

@Module
object SettingsFeatureNavigationModule {
    @Provides
    @IntoSet
    @NavigationScope
    fun provideTransactionFeatureImpl(
        appComponent: AppComponent
    ): FeatureApi {
        return SettingsFeatureImpl(appComponent)
    }
}
