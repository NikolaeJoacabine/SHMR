package com.nikol.transaction.screens.income.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @IncomeScreenScope
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

    @IncomeScreenScope
    @Provides
    fun provideCalculateTotalUseCase(): CalculateTotalUseCase {
        return CalculateTotalUseCase()
    }
}
