package com.nikol.domain.useCase

import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.CreateTransactionState

class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(createTransaction: CreateTransaction): CreateTransactionState {
        return transactionRepository.createTransaction(createTransaction)
    }
}
