package com.nikol.transaction.screens.income.di

import com.nikol.transaction.di.TransactionFeatureComponent
import com.nikol.transaction.screens.income.IncomeScreenViewModel
import dagger.Component

@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [UseCaseModule::class]
)
@IncomeScreenScope
interface IncomeScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            transactionFeatureComponent: TransactionFeatureComponent
        ): IncomeScreenComponent
    }

    fun viewModelFactory(): IncomeScreenViewModel.Factory.Factory
}
