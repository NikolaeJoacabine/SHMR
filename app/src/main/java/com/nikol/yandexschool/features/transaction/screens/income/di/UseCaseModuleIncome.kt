package com.nikol.yandexschool.features.transaction.screens.income.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleIncome {

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
