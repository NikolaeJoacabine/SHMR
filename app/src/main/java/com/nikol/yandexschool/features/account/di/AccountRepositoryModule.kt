package com.nikol.yandexschool.features.account.di

import com.nikol.data.account.AccountRepositoryImpl
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepositoryImpl
import com.nikol.data.network.FinanceAPI
import com.nikol.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides

@Module
class AccountRepositoryModule {

    @AccountComponentScope
    @Provides
    fun provideRemoteAccountRepository(financeAPI: FinanceAPI): RemoteAccountRepository {
        return RemoteAccountRepositoryImpl(financeAPI)
    }

    @AccountComponentScope
    @Provides
    fun provideAccountRepository(remoteAccountRepository: RemoteAccountRepository): AccountRepository {
        return AccountRepositoryImpl(remoteAccountRepository)
    }

}