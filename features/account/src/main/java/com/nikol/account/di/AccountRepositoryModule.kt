package com.nikol.account.di

import com.nikol.data.account.AccountRepositoryImpl
import com.nikol.data.account.local.dataStore.AccountPreferencesDataSource
import com.nikol.data.account.local.LocalAccountRepository
import com.nikol.data.account.local.LocalAccountRepositoryImpl
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepositoryImpl
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.sync.conflictStrategy.LastWriteWinsTransactionStrategy
import com.nikol.data.sync.conflictStrategy.SyncConflictStrategy
import com.nikol.data.transaction.TransactionRepositoryImpl
import com.nikol.data.transaction.local.LocalTransactionRepository
import com.nikol.data.transaction.local.LocalTransactionRepositoryImpl
import com.nikol.data.transaction.local.database.TransactionEntity
import com.nikol.data.transaction.remote.RemoteTransactionRepository
import com.nikol.data.transaction.remote.RemoteTransactionRepositoryImpl
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
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


    @AccountComponentScope
    @Binds
    fun bindsLocalTransactionRepository(
        localTransactionRepository: LocalTransactionRepositoryImpl
    ) : LocalTransactionRepository

    @AccountComponentScope
    @Binds
    fun bindsRemoteTransactionRepository(
        remoteTransactionRepository: RemoteTransactionRepositoryImpl
    ) : RemoteTransactionRepository

    @AccountComponentScope
    @Binds
    fun bindsTransactionRepository(
        transactionRepository: TransactionRepositoryImpl
    ) : TransactionRepository

    @AccountComponentScope
    @Binds
    fun bindsConflictStrategy(
        lastWriteWinsTransactionStrategy: LastWriteWinsTransactionStrategy
    ): SyncConflictStrategy<TransactionEntity>
}
