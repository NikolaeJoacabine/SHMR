package com.nikol.domain.state

import com.nikol.domain.model.Account

sealed class AccountState {
    data class Success(val items: List<Account>) : AccountState()
    data class Error(val message: String) : AccountState()
}