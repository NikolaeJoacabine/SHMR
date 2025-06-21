package com.nikol.data.account

import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountState

class AccountRepositoryImpl(private val remoteAccountRepository: RemoteAccountRepository) :
    AccountRepository {
    override suspend fun getAccount(): AccountState {
        return remoteAccountRepository.getAccount()
    }
}