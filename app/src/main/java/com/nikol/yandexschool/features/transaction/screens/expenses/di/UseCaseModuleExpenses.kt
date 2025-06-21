package com.nikol.yandexschool.features.transaction.screens.expenses.di

import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import com.nikol.domain.useCase.GetTransactionsUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleExpenses {

    @ExpensesScreenScope
    @Provides
    fun provideGetTransactionsForTodayUseCase(transactionRepository: TransactionRepository): GetTransactionsForTodayUseCase {
        return GetTransactionsForTodayUseCase(transactionRepository)
    }

    @ExpensesScreenScope
    @Provides
    fun provideCalculateTotalUseCase(): CalculateTotalUseCase {
        return CalculateTotalUseCase()
    }
}