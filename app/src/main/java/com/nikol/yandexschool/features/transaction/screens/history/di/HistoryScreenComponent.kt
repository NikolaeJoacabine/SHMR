package com.nikol.yandexschool.features.transaction.screens.history.di

import android.content.Context
import com.nikol.yandexschool.features.transaction.di.TransactionFeatureComponent
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreenViewModelFactoryFactory
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [HistoryScreenModule::class]
)
@HistoryScreenScope
interface HistoryScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            transactionFeatureComponent: TransactionFeatureComponent
        ): HistoryScreenComponent
    }

    fun viewModelFactoryFactory(): HistoryScreenViewModelFactoryFactory
}