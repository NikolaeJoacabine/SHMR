package com.nikol.yandexschool.features.transaction.screens.income.di

import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import com.nikol.yandexschool.features.transaction.screens.income.IncomeScreenViewModelFactoryFactory
import dagger.Module
import dagger.Provides

@Module(includes = [UseCaseModuleIncome::class])
class IncomeScreenModule {
    @Provides
    @IncomeScreenScope
    fun provideIncomeScreenViewModelFactoryFactory(
        getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
        calculateTotalUseCase: CalculateTotalUseCase
    ): IncomeScreenViewModelFactoryFactory {
        return IncomeScreenViewModelFactoryFactory(
            getTransactionsForTodayUseCase,
            calculateTotalUseCase
        )
    }
}
