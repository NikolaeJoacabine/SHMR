package com.nikol.yandexschool.features.transaction.screens.transaction.di

import android.content.Context
import com.nikol.yandexschool.features.articles.di.ArticlesFeatureComponent
import com.nikol.yandexschool.features.articles.di.ArticlesFeatureModule
import com.nikol.yandexschool.features.transaction.di.TransactionFeatureComponent
import com.nikol.yandexschool.features.transaction.screens.transaction.TransactionScreenViewModelFactoryFactory
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [TransactionScreenModule::class]
)
@TransactionScreenScope
interface TransactionScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            featureComponent: TransactionFeatureComponent
        ): TransactionScreenComponent
    }

    fun viewModelFactoryFactory(): TransactionScreenViewModelFactoryFactory
}
