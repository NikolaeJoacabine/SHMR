package com.nikol.yandexschool.features.account.screens.account.di

import com.nikol.domain.useCase.GetAccountUseCase
import com.nikol.yandexschool.features.account.screens.account.AccountScreenViewModelFactoryFactory
import dagger.Module
import dagger.Provides

@Module(
    includes = [UseCaseModuleAccount::class]
)
class AccountScreenModule {

    @AccountScreenScope
    @Provides
    fun provideAccountScreenViewModelFactoryFactory(getAccountUseCase: GetAccountUseCase): AccountScreenViewModelFactoryFactory {
        return AccountScreenViewModelFactoryFactory(getAccountUseCase)
    }
}