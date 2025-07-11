package com.nikol.domain.useCase

import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.DetailTransactionState

class GetTransactionByIdUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(id: Int): DetailTransactionState {
        return transactionRepository.getDetailTransaction(id)
    }
}
