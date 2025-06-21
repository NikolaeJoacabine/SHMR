package com.nikol.yandexschool.features.transaction.screens.expenses.di

import android.content.Context
import com.nikol.yandexschool.features.transaction.di.TransactionFeatureComponent
import com.nikol.yandexschool.features.transaction.screens.expenses.ExpensesScreenViewModelFactoryFactory
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [ExpensesScreenModule::class]
)
@ExpensesScreenScope
interface ExpensesScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            featureComponent: TransactionFeatureComponent
        ): ExpensesScreenComponent
    }

    fun viewModelFactoryFactory(): ExpensesScreenViewModelFactoryFactory
}
