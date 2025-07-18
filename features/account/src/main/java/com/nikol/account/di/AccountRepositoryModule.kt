package com.nikol.account.di

import com.nikol.data.account.AccountRepositoryImpl
import com.nikol.data.account.local.dataStore.AccountPreferencesDataSource
import com.nikol.data.account.local.LocalAccountRepository
import com.nikol.data.account.local.LocalAccountRepositoryImpl
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepositoryImpl
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AccountRepositoryModule {

    @AccountComponentScope
    @Binds
    fun bindsRemoteAccountRepository(
        remoteAccountRepositoryImpl: RemoteAccountRepositoryImpl
    ): RemoteAccountRepository

    @AccountComponentScope
    @Binds
    fun bindsLocalAccountRepository(
        localAccountRepositoryImpl: LocalAccountRepositoryImpl
    ): LocalAccountRepository

    @AccountComponentScope
    @Binds
    fun bindsAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository
}
