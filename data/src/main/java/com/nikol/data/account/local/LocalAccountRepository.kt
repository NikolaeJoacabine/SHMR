package com.nikol.data.account.local

import android.icu.util.Currency
import com.nikol.data.account.local.database.AccountEntity
import com.nikol.domain.model.Account

/**
 * Интерфейс для локального хранилища текущего идентификатора аккаунта.
 * Определяет операции сохранения и получения текущего ID аккаунта.
 */
interface LocalAccountRepository {

    /**
     * Сохраняет идентификатор текущего аккаунта в локальное хранилище.
     *
     * @param id идентификатор аккаунта для сохранения.
     */
    suspend fun saveCurrentAccountId(id: Int)

    /**
     * Получает сохранённый идентификатор текущего аккаунта из локального хранилища.
     *
     * @return идентификатор аккаунта или null, если он не был сохранён.
     */
    suspend fun getCurrentAccountId(): Int?

    suspend fun getCurrentCurrency(): String

    suspend fun saveCurrency(currency: String)

    suspend fun getAllAccounts(): List<AccountEntity>

    suspend fun insertAll(accounts: List<AccountEntity>)

    suspend fun getAccountById(id: Int): AccountEntity?

    suspend fun deleteAccount(id: Int): Boolean

    suspend fun insertAccount(account: AccountEntity)
    suspend fun upsertAll(accounts: List<AccountEntity>)
    suspend fun getTransactionCountForAccount(id: Int): Int
    suspend fun getDeletedAccountIds(): List<Int>
    suspend fun removeDeletedId(id: Int)
    suspend fun saveDeletedAccountId(id: Int)
    suspend fun updateAccount(account: AccountEntity)
    suspend fun getUnsyncedAccounts(): List<AccountEntity>
}
