package com.nikol.domain.useCase

import com.nikol.domain.model.TransactionType
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.TransactionState
import com.nikol.domain.state.TransactionState.*
import java.time.LocalDate

class GetTransactionsByPeriodUseCase(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState {
        val result = transactionRepository.getTransactionsByPeriod(startDate, endDate)
        return when (result) {
            is Success -> {
                val filteredList = result.items.filter {
                    if (type == TransactionType.Expenses) !it.isIncome else it.isIncome
                }
                Success(filteredList)
            }

            is NetworkError -> NetworkError
            is Error -> Error("")
        }
    }
}
