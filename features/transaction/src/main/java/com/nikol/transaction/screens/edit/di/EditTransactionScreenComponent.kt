package com.nikol.transaction.screens.edit.di

import com.nikol.transaction.di.TransactionFeatureComponent
import com.nikol.transaction.screens.edit.EditTransactionScreenViewModel
import dagger.Component

@EditTransactionScreenScope
@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [UseCaseModule::class]
)
internal interface EditTransactionScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            transactionFeatureComponent: TransactionFeatureComponent
        ): EditTransactionScreenComponent
    }

    fun viewModelFactory(): EditTransactionScreenViewModel.Factory.Factory
}
