package com.nikol.transaction.screens.edit.di

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.useCase.DeleteTransactionUseCase
import com.nikol.domain.useCase.GetAllAccountUseCase
import com.nikol.domain.useCase.GetFilteredArticlesUseCase
import com.nikol.domain.useCase.GetTransactionByIdUseCase
import com.nikol.domain.useCase.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @Provides
    @EditTransactionScreenScope
    fun provideGetTransactionByIdUseCase(
        transactionRepository: TransactionRepository
    ): GetTransactionByIdUseCase {
        return GetTransactionByIdUseCase(transactionRepository)
    }

    @Provides
    @EditTransactionScreenScope
    fun provideGetFilteredArticlesUseCase(
        articlesRepository: ArticlesRepository
    ): GetFilteredArticlesUseCase {
        return GetFilteredArticlesUseCase(articlesRepository)
    }

    @Provides
    @EditTransactionScreenScope
    fun provideGetAllAccountUseCase(
        accountRepository: AccountRepository
    ): GetAllAccountUseCase {
        return GetAllAccountUseCase(accountRepository)
    }

    @Provides
    @EditTransactionScreenScope
    fun providesUpdateTransactionUseCase(
        transactionRepository: TransactionRepository
    ): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(transactionRepository)
    }

    @Provides
    @EditTransactionScreenScope
    fun providesDeleteTransactionUseCase(
        transactionRepository: TransactionRepository
    ): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(transactionRepository)
    }
}
