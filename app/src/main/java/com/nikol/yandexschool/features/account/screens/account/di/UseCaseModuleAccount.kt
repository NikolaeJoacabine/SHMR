package com.nikol.yandexschool.features.account.screens.account.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.useCase.GetAccountUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleAccount {

    @AccountScreenScope
    @Provides
    fun provideGetAccountUseCase(
        accountRepository: AccountRepository
    ): GetAccountUseCase {
        return GetAccountUseCase(accountRepository)
    }
}
