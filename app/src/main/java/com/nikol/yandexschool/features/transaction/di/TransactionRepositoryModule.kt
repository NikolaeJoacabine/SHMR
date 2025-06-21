package com.nikol.yandexschool.features.transaction.di

import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.transaction.TransactionRepositoryImpl
import com.nikol.data.transaction.remote.RemoteTransactionRepository
import com.nikol.data.transaction.remote.RemoteTransactionRepositoryImpl
import com.nikol.data.util.timeProvider.DefaultTimeProvider
import com.nikol.domain.common.TimeProvider
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
    fun provideTransactionRepository(remoteTransactionRepository: RemoteTransactionRepository): TransactionRepository {
        return TransactionRepositoryImpl(remoteTransactionRepository)
    }

    @TransactionComponentScope
    @Provides
    fun provideTimeProvider(): TimeProvider {
        return DefaultTimeProvider()
    }
}