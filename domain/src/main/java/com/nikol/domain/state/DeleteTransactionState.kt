package com.nikol.domain.state

sealed class DeleteTransactionState {
    data object Success : DeleteTransactionState()
    data object Error : DeleteTransactionState()
    data object NoInternet : DeleteTransactionState()
    data object NotFound : DeleteTransactionState()
}
