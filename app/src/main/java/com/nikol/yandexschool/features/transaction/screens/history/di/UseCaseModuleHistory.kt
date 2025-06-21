package com.nikol.yandexschool.features.transaction.screens.history.di

import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsByPeriodUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleHistory {

    @HistoryScreenScope
    @Provides
    fun provideCalculateTotalUseCase(): CalculateTotalUseCase {
        return CalculateTotalUseCase()
    }

    @HistoryScreenScope
    @Provides
    fun provideGetTransactionsByPeriodUseCase(transactionRepository: TransactionRepository): GetTransactionsByPeriodUseCase {
        return GetTransactionsByPeriodUseCase(transactionRepository)
    }
}