package com.nikol.domain.state

import com.nikol.domain.model.Transaction

sealed class TransactionState {
    data class Success(val items: List<Transaction>) : TransactionState()
    data object NetworkError : TransactionState()
    data class Error(val message: String) : TransactionState()
}