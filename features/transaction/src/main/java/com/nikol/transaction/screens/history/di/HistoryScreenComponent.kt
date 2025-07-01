package com.nikol.transaction.screens.history.di

import com.nikol.transaction.di.TransactionFeatureComponent
import com.nikol.transaction.screens.history.HistoryScreenViewModel
import dagger.Component

@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [UseCaseModule::class]
)
@HistoryScreenScope
interface HistoryScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            transactionFeatureComponent: TransactionFeatureComponent
        ): HistoryScreenComponent
    }

    fun viewModelFactory(): HistoryScreenViewModel.Factory.Factory
}
