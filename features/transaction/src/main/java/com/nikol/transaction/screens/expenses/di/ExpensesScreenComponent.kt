package com.nikol.transaction.screens.expenses.di

import com.nikol.transaction.di.TransactionFeatureComponent
import com.nikol.transaction.screens.expenses.ExpensesScreenViewModel
import dagger.Component

@Component(dependencies = [TransactionFeatureComponent::class], modules = [UseCaseModule::class])
@ExpensesScreenScope
interface ExpensesScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            transactionFeatureComponent: TransactionFeatureComponent
        ): ExpensesScreenComponent
    }

    fun viewModelFactory(): ExpensesScreenViewModel.Factory.Factory
}
