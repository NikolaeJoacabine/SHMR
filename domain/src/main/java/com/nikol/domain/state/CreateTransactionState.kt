package com.nikol.domain.state

sealed class CreateTransactionState{
    data object Success : CreateTransactionState()
    data object Error : CreateTransactionState()
    data object NetworkError: CreateTransactionState()
    data object NotFound : CreateTransactionState()
}
