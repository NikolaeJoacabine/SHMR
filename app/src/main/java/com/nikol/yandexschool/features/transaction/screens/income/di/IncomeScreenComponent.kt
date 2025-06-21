package com.nikol.yandexschool.features.transaction.screens.income.di

import android.content.Context
import com.nikol.yandexschool.features.transaction.di.TransactionFeatureComponent
import com.nikol.yandexschool.features.transaction.screens.income.IncomeScreenViewModelFactoryFactory
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [TransactionFeatureComponent::class], modules = [IncomeScreenModule::class]
)
@IncomeScreenScope
interface IncomeScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            transactionFeatureComponent: TransactionFeatureComponent
        ): IncomeScreenComponent
    }

    fun viewModelFactoryFactory(): IncomeScreenViewModelFactoryFactory
}