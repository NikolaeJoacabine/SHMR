package com.nikol.data.account.remote

import com.nikol.domain.state.AccountState

interface RemoteAccountRepository {
    suspend fun getAccount(): AccountState
}