package com.nikol.account.di

import com.nikol.data.account.AccountRepositoryImpl
import com.nikol.data.account.local.AccountPreferencesDataSource
import com.nikol.data.account.local.LocalAccountRepository
import com.nikol.data.account.local.LocalAccountRepositoryImpl
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepositoryImpl
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides

@Module
object AccountRepositoryModule {

    @AccountComponentScope
    @Provides
    fun provideRemoteAccountRepository(
        financeAPI: FinanceAPI,
        networkStatusProvider: NetworkStatusProvider
    ): RemoteAccountRepository {
        return RemoteAccountRepositoryImpl(financeAPI, networkStatusProvider)
    }

    @AccountComponentScope
    @Provides
    fun provideLocalAccountRepository(
        accountPreferencesDataSource: AccountPreferencesDataSource
    ): LocalAccountRepository {
        return LocalAccountRepositoryImpl(accountPreferencesDataSource)
    }

    @AccountComponentScope
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

}
