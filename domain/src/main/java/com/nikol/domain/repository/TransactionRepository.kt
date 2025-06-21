package com.nikol.domain.repository

import com.nikol.domain.state.TransactionState
import java.time.LocalDate

interface TransactionRepository {

    suspend fun getAllTransaction(): TransactionState
    suspend fun getTransactionsForToday(): TransactionState
    suspend fun getTransactionsByPeriod(startDate: LocalDate, endDate: LocalDate): TransactionState
}