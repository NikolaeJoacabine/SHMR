package com.nikol.yandexschool.features.transaction.screens.transaction.di

import com.nikol.domain.useCase.GetTransactionsUseCase
import com.nikol.yandexschool.features.transaction.screens.transaction.TransactionScreenViewModelFactoryFactory
import dagger.Module
import dagger.Provides

@Module(includes = [UseCaseModuleTransaction::class])
class TransactionScreenModule {
    @Provides
    @TransactionScreenScope
    fun provideTransactionScreenViewModelFactoryFactory(
        getTransactionsUseCase: GetTransactionsUseCase
    ): TransactionScreenViewModelFactoryFactory {
        return TransactionScreenViewModelFactoryFactory(getTransactionsUseCase)
    }
}