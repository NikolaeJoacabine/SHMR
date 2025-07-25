package com.nikol.account.screens.account.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.GetAccountUseCase
import com.nikol.domain.useCase.GetAllCurrencyUseCase
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import com.nikol.domain.useCase.GetStatisticForAccount
import com.nikol.domain.useCase.SaveCurrencyUseCase
import dagger.Module
import dagger.Provides

@Module
internal object UseCaseModule {

    @AccountScreenScope
    @Provides
    fun provideGetAccountUseCase(
        accountRepository: AccountRepository
    ): GetAccountUseCase {
        return GetAccountUseCase(accountRepository)
    }

    @Provides
    @AccountScreenScope
    fun provideGetAllCurrencyUseCase(): GetAllCurrencyUseCase {
        return GetAllCurrencyUseCase()
    }

    @Provides
    @AccountScreenScope
    fun provideGetCurrentCurrencyUseCase(
        accountRepository: AccountRepository
    ): GetCurrentCurrencyUseCase {
        return GetCurrentCurrencyUseCase(accountRepository)
    }

    @Provides
    @AccountScreenScope
    fun provideSaveCurrencyUseCase(
        accountRepository: AccountRepository
    ): SaveCurrencyUseCase {
        return SaveCurrencyUseCase(accountRepository)
    }

    @Provides
    @AccountScreenScope
    fun provideGetStatisticForAccountUseCase(
        transactionRepository: TransactionRepository,
        accountRepository: AccountRepository
    ): GetStatisticForAccount {
        return GetStatisticForAccount(
            transactionRepository,
            accountRepository
        )
    }
}
