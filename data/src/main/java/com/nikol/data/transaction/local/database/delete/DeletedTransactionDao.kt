package com.nikol.data.transaction.local.database.delete

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikol.data.transaction.local.database.delete.DeletedTransactionEntity

@Dao
interface DeletedTransactionDao {
    @Query("SELECT * FROM deleted_transactions")
    suspend fun getAll(): List<DeletedTransactionEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(entity: DeletedTransactionEntity)

    @Query("DELETE FROM deleted_transactions WHERE id = :id")
    suspend fun remove(id: Int)
}