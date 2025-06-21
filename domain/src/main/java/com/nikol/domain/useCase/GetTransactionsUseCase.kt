package com.nikol.domain.useCase

import com.nikol.domain.model.TransactionType
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.TransactionState
import com.nikol.domain.state.TransactionState.*

class GetTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(type: TransactionType): TransactionState {

        val result = transactionRepository.getAllTransaction()
        return when (result) {
            is Success -> {
                val list = result.items
                    .filter { if (type == TransactionType.Expenses) it.isIncome == false else it.isIncome == true }
                Success(list)
            }

            is Error -> Error("")
            is NetworkError -> NetworkError
        }
    }
}