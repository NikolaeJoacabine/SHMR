package com.nikol.yandexschool.di

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
import com.nikol.data.sync.SyncableRepository
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
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface SyncModule {

    @Binds
    @Singleton
    fun bindTransactionConflictStrategy(
        impl: LastWriteWinsTransactionStrategy
    ): SyncConflictStrategy<TransactionEntity>

    // === TRANSACTIONS ===

    @Binds
    @Singleton
    fun bindRemoteTransactionRepository(impl: RemoteTransactionRepositoryImpl): RemoteTransactionRepository

    @Binds
    @Singleton
    fun bindLocalTransactionRepository(impl: LocalTransactionRepositoryImpl): LocalTransactionRepository

    @Binds
    @IntoSet
    @Singleton
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): SyncableRepository


    // === ACCOUNTS ===

    @Binds
    @Singleton
    fun bindRemoteAccountRepository(impl: RemoteAccountRepositoryImpl): RemoteAccountRepository

    @Binds
    @Singleton
    fun bindLocalAccountRepository(impl: LocalAccountRepositoryImpl): LocalAccountRepository

    @Binds
    @IntoSet
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): SyncableRepository


    // === ARTICLES ===

    @Binds
    @Singleton
    fun bindRemoteArticlesRepository(impl: RemoteArticlesRepositoryImpl): RemoteArticlesRepository

    @Binds
    @Singleton
    fun bindLocalArticlesRepository(impl: LocalArticlesRepositoryImpl): LocalArticlesRepository

    @Binds
    @IntoSet
    @Singleton
    fun bindArticlesRepository(impl: ArticlesRepositoryImpl): SyncableRepository
}
