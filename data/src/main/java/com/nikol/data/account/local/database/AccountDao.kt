package com.nikol.data.account.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accounts: List<AccountEntity>)

    @Query("SELECT * FROM accounts WHERE account_id = :id")
    suspend fun getAccountById(id: Int): AccountEntity?

    @Query("DELETE FROM accounts WHERE account_id = :id")
    suspend fun deleteAccountById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccounts(accounts: List<AccountEntity>): List<Long>
    @Update
    suspend fun updateAccounts(accounts: List<AccountEntity>)

    @Transaction
    suspend fun upsertAll(accounts: List<AccountEntity>) {
        val insertResult = insertAccounts(accounts)
        val toUpdate = accounts.mapIndexedNotNull { index, account ->
            if (insertResult[index] == -1L) account else null
        }
        if (toUpdate.isNotEmpty()) updateAccounts(toUpdate)
    }
    @Query("SELECT * FROM accounts WHERE is_synced = 0")
    suspend fun getUnsynced(): List<AccountEntity>

    @Delete
    suspend fun delete(account: AccountEntity)

    @Update
    suspend fun updateAccount(account: AccountEntity)
}
