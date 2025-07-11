package com.nikol.transaction.screens.add.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.CreateTransactionUseCase
import com.nikol.domain.useCase.DeleteTransactionUseCase
import com.nikol.domain.useCase.GetAllAccountUseCase
import com.nikol.domain.useCase.GetFilteredArticlesUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @AddTransactionScreenScope
    @Provides
    fun provideGetAllAccountUseCase(
        accountRepository: AccountRepository
    ): GetAllAccountUseCase {
        return GetAllAccountUseCase(accountRepository)
    }

    @AddTransactionScreenScope
    @Provides
    fun provideGetFilteredArticlesUseCase(
        articlesRepository: ArticlesRepository
    ): GetFilteredArticlesUseCase {
        return GetFilteredArticlesUseCase(articlesRepository)
    }

    @AddTransactionScreenScope
    @Provides
    fun provideCreateTransactionUseCase(
        transactionRepository: TransactionRepository
    ): CreateTransactionUseCase {
        return CreateTransactionUseCase(transactionRepository)
    }
}
