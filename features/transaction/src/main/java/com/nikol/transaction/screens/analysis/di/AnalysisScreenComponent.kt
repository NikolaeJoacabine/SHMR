package com.nikol.transaction.screens.analysis.di

import com.nikol.transaction.di.TransactionFeatureComponent
import com.nikol.transaction.screens.analysis.AnalysisScreenViewModel
import dagger.Component

@AnalysisScreenScope
@Component(
    dependencies = [TransactionFeatureComponent::class],
    modules = [UseCaseModule::class]
)
interface AnalysisScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            transactionFeatureComponent: TransactionFeatureComponent
        ): AnalysisScreenComponent
    }

    fun viewModelFactory(): AnalysisScreenViewModel.Factory.Factory
}