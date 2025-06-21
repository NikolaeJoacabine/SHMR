package com.nikol.data.transaction

import com.nikol.data.transaction.remote.RemoteTransactionRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.TransactionState
import java.time.LocalDate

class TransactionRepositoryImpl(
    private val remoteTransactionRepository: RemoteTransactionRepository
) : TransactionRepository {
    override suspend fun getAllTransaction(): TransactionState {
        return remoteTransactionRepository.transactionForThePeriod()
    }

    override suspend fun getTransactionsForToday(): TransactionState {
        return remoteTransactionRepository.transactionForToday()
    }

    override suspend fun getTransactionsByPeriod(
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState {
        return remoteTransactionRepository.getTransactionsByPeriod(startDate, endDate)
    }
}