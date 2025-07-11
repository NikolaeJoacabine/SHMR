package com.nikol.domain.useCase

import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.DeleteTransactionState

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transactionId: Int): DeleteTransactionState {
        return repository.deleteTransaction(transactionId)
    }
}
