package com.nikol.data.transaction.local

import com.nikol.data.articles.local.database.ArticleEntity
import com.nikol.data.transaction.local.database.TransactionEntity
import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionDetail
import java.time.LocalDate

interface LocalTransactionRepository {

    suspend fun transactionForToday(id: Int): List<Transaction>

    suspend fun getTransactionsByPeriod(
        id: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Transaction>

    suspend fun createTransaction(
        transactionEntity: TransactionEntity
    )

    suspend fun getTransactionById(id: Int): TransactionDetail

    suspend fun updateTransaction(transactionEntity: TransactionEntity)

    suspend fun deleteTransaction(id: Int)

    suspend fun insertAll(transactions: List<TransactionEntity>)

    suspend fun createTransaction(
        createTransaction: CreateTransaction,
        id: Int,
        isSynced: Boolean,
        lastSyncedAt: Long?
    )

    suspend fun getArticleById(id: Int): ArticleEntity
    suspend fun getTransactionEntityById(id: Int): TransactionEntity

    suspend fun getUnsyncedTransactions(): List<TransactionEntity>
    suspend fun getDeletedTransactionIds(): List<Int>
    suspend fun removeDeletedId(id: Int)
    suspend fun markAsSynced(entity: TransactionEntity)
    suspend fun markAsDeleted(id: Int)
    suspend fun hasUnsynced(): Boolean
}
