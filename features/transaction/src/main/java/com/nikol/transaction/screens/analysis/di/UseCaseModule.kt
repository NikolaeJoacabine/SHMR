package com.nikol.transaction.screens.analysis.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import com.nikol.domain.useCase.GetListWithAnalysisUseCase
import com.nikol.domain.useCase.ValidateDateRangeUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @AnalysisScreenScope
    @Provides
    fun provideGetCurrencyUseCase(
        accountRepository: AccountRepository
    ): GetCurrentCurrencyUseCase {
        return GetCurrentCurrencyUseCase(accountRepository)
    }

    @AnalysisScreenScope
    @Provides
    fun provideCalculateTotalUseCase(): CalculateTotalUseCase {
        return CalculateTotalUseCase()
    }

    @AnalysisScreenScope
    @Provides
    fun provideGetListWithAnalysisUseCase(
        accountRepository: AccountRepository,
        transactionRepository: TransactionRepository
    ): GetListWithAnalysisUseCase {
        return GetListWithAnalysisUseCase(
            transactionRepository,
            accountRepository
        )
    }

    @AnalysisScreenScope
    @Provides
    fun provideValidateDateRangeUseCase(): ValidateDateRangeUseCase {
        return ValidateDateRangeUseCase()
    }
}
