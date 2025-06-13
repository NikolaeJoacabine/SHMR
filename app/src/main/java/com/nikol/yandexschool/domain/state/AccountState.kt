package com.nikol.yandexschool.domain.state

import com.nikol.yandexschool.domain.model.Account

sealed class AccountState {
    object Loading : AccountState()
    data class Success(val items: List<Account>) : AccountState()
    data class Error(val message: String) : AccountState()
}