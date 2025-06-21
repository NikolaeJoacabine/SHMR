package com.nikol.yandexschool.features.transaction.screens.expenses.di

import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import com.nikol.domain.useCase.GetTransactionsUseCase
import com.nikol.yandexschool.features.transaction.screens.expenses.ExpensesScreenViewModelFactoryFactory
import dagger.Module
import dagger.Provides

@Module(includes = [UseCaseModuleExpenses::class])
class ExpensesScreenModule {
    @Provides
    @ExpensesScreenScope
    fun provideTransactionScreenViewModelFactoryFactory(
        getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
        calculateTotalUseCase: CalculateTotalUseCase
    ): ExpensesScreenViewModelFactoryFactory {
        return ExpensesScreenViewModelFactoryFactory(getTransactionsForTodayUseCase, calculateTotalUseCase)
    }
}