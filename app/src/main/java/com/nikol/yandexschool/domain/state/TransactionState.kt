package com.nikol.yandexschool.domain.state

import com.nikol.yandexschool.domain.model.Transaction

sealed class TransactionState {
    object Loading : TransactionState()
    data class Success(val items: List<Transaction>) : TransactionState()
    data class Error(val message: String) : TransactionState()
}