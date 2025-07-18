package com.nikol.data.account.local.database.deleted

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_account_ids")
data class DeletedAccountIdEntity(
    @PrimaryKey val id: Int
)
