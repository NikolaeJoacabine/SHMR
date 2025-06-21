package com.nikol.yandexschool.features.transaction.screens.history.di

import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsByPeriodUseCase
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreenViewModelFactoryFactory
import dagger.Module
import dagger.Provides

@Module(includes = [UseCaseModuleHistory::class])
class HistoryScreenModule {

    @HistoryScreenScope
    @Provides
    fun provideHistoryScreenViewModelFactoryFactory(
        calculateTotalUseCase: CalculateTotalUseCase,
        getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
    ): HistoryScreenViewModelFactoryFactory {
        return HistoryScreenViewModelFactoryFactory(
            calculateTotalUseCase,
            getTransactionsByPeriodUseCase
        )
    }
}