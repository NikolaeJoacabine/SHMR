package com.nikol.domain.useCase

import com.nikol.domain.model.Analysis
import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionType
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.AccountIdState
import com.nikol.domain.state.TransactionState
import java.time.LocalDate
import java.time.ZoneOffset

class GetStatisticForAccount(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(): List<Analysis> {
        return when (val responseId = accountRepository.getCurrentAccountId()) {
            is AccountIdState.Success -> {
                val result = transactionRepository.getTransactionsByPeriod(
                    id = responseId.id,
                    startDate = LocalDate.now().withDayOfMonth(1),
                    endDate = LocalDate.now()
                )
                when (result) {
                    is TransactionState.Error -> emptyList()
                    is TransactionState.NetworkError -> emptyList()
                    is TransactionState.Success -> getAnalysisList(result.items)
                }
            }

            is AccountIdState.NoInternet -> emptyList()
            is AccountIdState.Error -> emptyList()
        }
    }

    private fun getAnalysisList(transactions: List<Transaction>): List<Analysis> {
        return transactions
            .groupBy { it.createdAt.atZone(ZoneOffset.UTC).toLocalDate() }
            .map { (date, transactions) ->
                val amount = transactions.sumOf { if (it.isIncome) it.amount else -it.amount }

                Analysis(
                    isNegative = amount < 0,
                    amount = amount,
                    date = date
                )
            }
            .sortedBy { it.date }
    }
}