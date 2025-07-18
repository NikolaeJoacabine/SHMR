package com.nikol.data.account.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    indices = [
        Index(value = ["user_id"])
    ]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "account_id")
    val accountId: Int,

    @ColumnInfo(name = "balance")
    val balance: String,

    @ColumnInfo(name = "emoji")
    val emoji: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "currency_code")
    val currency: String,

    @ColumnInfo(name = "account_name")
    val name: String,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,

    @ColumnInfo(name = "last_synced_at")
    val lastSyncedAt: Long? = null
)
