package com.nikol.transaction.di

import com.nikol.data.account.AccountRepositoryImpl
import com.nikol.data.account.local.AccountPreferencesDataSource
import com.nikol.data.account.local.LocalAccountRepository
import com.nikol.data.account.local.LocalAccountRepositoryImpl
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepositoryImpl
import com.nikol.data.articles.ArticlesRepositoryImpl
import com.nikol.data.articles.remote.RemoteArticlesRepository
import com.nikol.data.articles.remote.RemoteArticlesRepositoryImpl
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.transaction.TransactionRepositoryImpl
import com.nikol.data.transaction.remote.RemoteTransactionRepository
import com.nikol.data.transaction.remote.RemoteTransactionRepositoryImpl
import com.nikol.data.util.timeProvider.DefaultTimeProvider
import com.nikol.domain.common.TimeProvider
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides

@Module
class TransactionRepositoryModule {

    @TransactionComponentScope
    @Provides
    fun provideRemoteTransactionRepository(
        financeAPI: FinanceAPI,
        networkStatusProvider: NetworkStatusProvider
    ): RemoteTransactionRepository {
        return RemoteTransactionRepositoryImpl(financeAPI, networkStatusProvider)
    }

    @TransactionComponentScope
    @Provides
    fun provideTransactionRepository(
        remoteTransactionRepository: RemoteTransactionRepository
    ): TransactionRepository {
        return TransactionRepositoryImpl(remoteTransactionRepository)
    }

    @TransactionComponentScope
    @Provides
    fun provideTimeProvider(): TimeProvider {
        return DefaultTimeProvider()
    }

    @TransactionComponentScope
    @Provides
    fun provideLocalAccountRepository(
        accountPreferencesDataSource: AccountPreferencesDataSource
    ): LocalAccountRepository {
        return LocalAccountRepositoryImpl(accountPreferencesDataSource)
    }

    @TransactionComponentScope
    @Provides
    fun provideRemoteAccountRepository(
        financeAPI: FinanceAPI,
        networkStatusProvider: NetworkStatusProvider
    ): RemoteAccountRepository {
        return RemoteAccountRepositoryImpl(financeAPI, networkStatusProvider)
    }

    @TransactionComponentScope
    @Provides
    fun provideAccountRepository(
        remoteAccountRepository: RemoteAccountRepository,
        localAccountRepository: LocalAccountRepository
    ): AccountRepository {
        return AccountRepositoryImpl(
            remoteAccountRepository,
            localAccountRepository
        )
    }

    @TransactionComponentScope
    @Provides
    fun provideRemoteArticlesRepository(
        financeAPI: FinanceAPI,
        networkStatusProvider: NetworkStatusProvider
    ): RemoteArticlesRepository {
        return RemoteArticlesRepositoryImpl(
            financeAPI,
            networkStatusProvider
        )
    }

    @TransactionComponentScope
    @Provides
    fun provideArticlesRepository(
        remoteArticlesRepository: RemoteArticlesRepository
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(remoteArticlesRepository)
    }
}
