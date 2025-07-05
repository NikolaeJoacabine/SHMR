package com.nikol.account.screens.edit.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.useCase.DeleteAccountUseCase
import com.nikol.domain.useCase.EditAccountUseCase
import com.nikol.domain.useCase.GetAccountByIdUseCase
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @Provides
    @EditScreenScope
    fun provideGetAccountByIdUseCase(
        accountRepository: AccountRepository
    ): GetAccountByIdUseCase {
        return GetAccountByIdUseCase(accountRepository)
    }

    @Provides
    @EditScreenScope
    fun provideGetCurrentCurrency(
        accountRepository: AccountRepository
    ): GetCurrentCurrencyUseCase {
        return GetCurrentCurrencyUseCase(accountRepository)
    }

    @Provides
    @EditScreenScope
    fun provideDeleteAccountUseCase(
        accountRepository: AccountRepository
    ): DeleteAccountUseCase {
        return DeleteAccountUseCase(accountRepository)
    }

    @Provides
    @EditScreenScope
    fun provideEditAccountUseCase(
        accountRepository: AccountRepository
    ): EditAccountUseCase {
        return EditAccountUseCase(accountRepository)
    }
}
