package com.nikol.transaction.di

import com.nikol.data.account.AccountRepositoryImpl
import com.nikol.data.account.local.LocalAccountRepository
import com.nikol.data.account.local.LocalAccountRepositoryImpl
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepositoryImpl
import com.nikol.data.articles.ArticlesRepositoryImpl
import com.nikol.data.articles.local.LocalArticlesRepository
import com.nikol.data.articles.local.LocalArticlesRepositoryImpl
import com.nikol.data.articles.remote.RemoteArticlesRepository
import com.nikol.data.articles.remote.RemoteArticlesRepositoryImpl
import com.nikol.data.sync.conflictStrategy.LastWriteWinsTransactionStrategy
import com.nikol.data.sync.conflictStrategy.SyncConflictStrategy
import com.nikol.data.transaction.TransactionRepositoryImpl
import com.nikol.data.transaction.local.LocalTransactionRepository
import com.nikol.data.transaction.local.LocalTransactionRepositoryImpl
import com.nikol.data.transaction.local.database.TransactionEntity
import com.nikol.data.transaction.remote.RemoteTransactionRepository
import com.nikol.data.transaction.remote.RemoteTransactionRepositoryImpl
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module

@Module
interface TransactionRepositoryModule {

    @TransactionComponentScope
    @Binds
    fun bindsLocalTransactionRepository(
        localTransactionRepositoryImpl: LocalTransactionRepositoryImpl
    ): LocalTransactionRepository

    @TransactionComponentScope
    @Binds
    fun bindsRemoteTransactionRepository(
        remoteTransactionRepositoryImpl: RemoteTransactionRepositoryImpl
    ): RemoteTransactionRepository

    @TransactionComponentScope
    @Binds
    fun bindsTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @TransactionComponentScope
    @Binds
    fun bindsLocalAccountRepository(
        localAccountRepositoryImpl: LocalAccountRepositoryImpl
    ): LocalAccountRepository

    @TransactionComponentScope
    @Binds
    fun bindsRemoteAccountRepository(
        remoteAccountRepositoryImpl: RemoteAccountRepositoryImpl
    ): RemoteAccountRepository

    @TransactionComponentScope
    @Binds
    fun bindsAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @TransactionComponentScope
    @Binds
    fun bindRemoteArticlesRepository(
        remoteArticlesRepositoryImpl: RemoteArticlesRepositoryImpl
    ): RemoteArticlesRepository

    @TransactionComponentScope
    @Binds
    fun bindsLocalArticlesRepository(
        localArticlesRepositoryImpl: LocalArticlesRepositoryImpl
    ): LocalArticlesRepository

    @TransactionComponentScope
    @Binds
    fun bindsArticlesRepository(
        articlesRepositoryImpl: ArticlesRepositoryImpl
    ): ArticlesRepository

    @TransactionComponentScope
    @Binds
    fun bindsConflictStrategy(
        lastWriteWinsTransactionStrategy: LastWriteWinsTransactionStrategy
    ): SyncConflictStrategy<TransactionEntity>
}
