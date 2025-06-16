package com.nikol.yandexschool.features.transaction.screens.transaction.di

import com.nikol.domain.useCase.GetTransactionsUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleTransaction {

    @TransactionScreenScope
    @Provides
    fun provideGetTransactionUseCase(): GetTransactionsUseCase {
        return GetTransactionsUseCase()
    }
}