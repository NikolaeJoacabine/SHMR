package com.nikol.account.screens.account.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.useCase.GetAccountUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @AccountScreenScope
    @Provides
    fun provideGetAccountUseCase(
        accountRepository: AccountRepository
    ): GetAccountUseCase {
        return GetAccountUseCase(accountRepository)
    }
}
