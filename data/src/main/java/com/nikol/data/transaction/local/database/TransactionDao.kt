package com.nikol.data.transaction.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {

    @Query("""
        SELECT * FROM transactions
        WHERE transaction_account_id = :id
          AND substr(created_at_server, 1, 10) = :targetDate
    """)
    suspend fun transactionForToday(id: Int, targetDate: String): List<TransactionEntity>

    @Query("""
        SELECT * FROM transactions
        WHERE transaction_account_id = :id
          AND substr(created_at_server, 1, 10) BETWEEN :startDate AND :endDate
    """)
    suspend fun getTransactionsByPeriod(
        id: Int,
        startDate: String,
        endDate: String
    ): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE transaction_id = :id")
    suspend fun getTransactionById(id: Int): TransactionEntity

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE transaction_id = :id")
    suspend fun deleteTransaction(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE is_synced = 0")
    suspend fun getUnsynced(): List<TransactionEntity>

    @Query("SELECT COUNT(*) FROM transactions WHERE transaction_account_id = :accountId")
    suspend fun getTransactionCountForAccount(accountId: Int): Int

}
