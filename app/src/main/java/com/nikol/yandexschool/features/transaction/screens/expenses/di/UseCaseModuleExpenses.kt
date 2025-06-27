package com.nikol.yandexschool.features.transaction.screens.expenses.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleExpenses {

    @ExpensesScreenScope
    @Provides
    fun provideGetTransactionsForTodayUseCase(
        transactionRepository: TransactionRepository,
        accountRepository: AccountRepository
    ): GetTransactionsForTodayUseCase {
        return GetTransactionsForTodayUseCase(
            transactionRepository,
            accountRepository
        )
    }

    @ExpensesScreenScope
    @Provides
    fun provideCalculateTotalUseCase(): CalculateTotalUseCase {
        return CalculateTotalUseCase()
    }
}
