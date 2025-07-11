package com.nikol.transaction.screens.add.di

import com.nikol.transaction.di.TransactionFeatureComponent
import com.nikol.transaction.screens.add.AddTransactionScreenViewModel
import dagger.Component

@AddTransactionScreenScope
@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [UseCaseModule::class]
)
internal interface AddTransactionScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            transactionFeatureComponent: TransactionFeatureComponent
        ): AddTransactionScreenComponent
    }

    fun viewModelFactory(): AddTransactionScreenViewModel.Factory.Factory
}
