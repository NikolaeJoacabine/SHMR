package com.nikol.domain.state

sealed class CreateTransactionState{
    data class Success(val id: Int) : CreateTransactionState()
    data class OfflineSaved(val localId: Int) : CreateTransactionState()
    data object Error : CreateTransactionState()
    data object NetworkError: CreateTransactionState()
    data object NotFound : CreateTransactionState()
}
