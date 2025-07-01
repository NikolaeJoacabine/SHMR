package com.nikol.transaction.screens.history.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsByPeriodUseCase
import com.nikol.domain.useCase.ValidateDateRangeUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @HistoryScreenScope
    @Provides
    fun provideCalculateTotalUseCase(): CalculateTotalUseCase {
        return CalculateTotalUseCase()
    }

    @HistoryScreenScope
    @Provides
    fun provideGetTransactionsByPeriodUseCase(
        transactionRepository: TransactionRepository,
        accountRepository: AccountRepository
    ): GetTransactionsByPeriodUseCase {
        return GetTransactionsByPeriodUseCase(
            transactionRepository,
            accountRepository
        )
    }

    @HistoryScreenScope
    @Provides
    fun provideValidateDateRangeUseCase(): ValidateDateRangeUseCase {
        return ValidateDateRangeUseCase()
    }
}
