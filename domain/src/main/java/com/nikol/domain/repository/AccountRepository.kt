package com.nikol.domain.repository

import com.nikol.domain.model.Account
import com.nikol.domain.state.AccountState

interface AccountRepository {
    suspend fun getAccount(): AccountState
}