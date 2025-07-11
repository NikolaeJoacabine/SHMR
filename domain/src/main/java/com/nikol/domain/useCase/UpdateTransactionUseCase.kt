package com.nikol.domain.useCase

import com.nikol.domain.model.UpdateTransaction
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.UpdateTransactionState

class UpdateTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(
        id: Int,
        transaction: UpdateTransaction
    ): UpdateTransactionState {
        return transactionRepository.updateTransaction(id, transaction)
    }
}
