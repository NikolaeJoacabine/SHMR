package com.nikol.data.transaction.local.database.delete

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_transactions")
data class DeletedTransactionEntity(
    @PrimaryKey val id: Int
)
