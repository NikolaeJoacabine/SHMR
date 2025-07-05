package com.nikol.transaction.screens.expenses.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {
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

    @ExpensesScreenScope
    @Provides
    fun provideGetCurrentCurrencyUseCase(
        accountRepository: AccountRepository
    ): GetCurrentCurrencyUseCase {
        return GetCurrentCurrencyUseCase(accountRepository)
    }
}
