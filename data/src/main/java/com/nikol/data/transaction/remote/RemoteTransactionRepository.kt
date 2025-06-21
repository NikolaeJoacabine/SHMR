package com.nikol.data.transaction.remote

import com.nikol.domain.state.TransactionState
import java.time.LocalDate

interface RemoteTransactionRepository {

    suspend fun transactionForThePeriod(): TransactionState

    suspend fun transactionForToday(): TransactionState

    suspend fun getTransactionsByPeriod(startDate: LocalDate, endDate: LocalDate): TransactionState
}