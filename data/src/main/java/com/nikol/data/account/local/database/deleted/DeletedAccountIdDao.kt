package com.nikol.data.account.local.database.deleted

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeletedAccountIdDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(id: DeletedAccountIdEntity)

    @Query("SELECT * FROM deleted_account_ids")
    suspend fun getAll(): List<DeletedAccountIdEntity>

    @Query("DELETE FROM deleted_account_ids WHERE id = :id")
    suspend fun remove(id: Int)
}
