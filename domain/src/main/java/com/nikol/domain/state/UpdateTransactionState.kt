package com.nikol.domain.state

sealed class UpdateTransactionState {
    data object Success : UpdateTransactionState()
    data object Error : UpdateTransactionState()
    data object NoInternet : UpdateTransactionState()
    data object NotFound : UpdateTransactionState()
}
