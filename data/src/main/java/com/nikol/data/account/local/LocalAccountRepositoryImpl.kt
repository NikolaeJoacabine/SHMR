package com.nikol.data.account.local

import com.nikol.data.account.local.dataStore.AccountPreferencesDataSource
import com.nikol.data.account.local.database.AccountDao
import com.nikol.data.account.local.database.AccountEntity
import com.nikol.data.account.local.database.deleted.DeletedAccountIdDao
import com.nikol.data.account.local.database.deleted.DeletedAccountIdEntity
import com.nikol.data.transaction.local.database.TransactionDao
import javax.inject.Inject

/**
 * Реализация [LocalAccountRepository], использующая [com.nikol.data.account.local.dataStore.AccountPreferencesDataSource]
 * для сохранения и получения текущего идентификатора аккаунта.
 */
class LocalAccountRepositoryImpl @Inject constructor(
    private val accountPreferencesDataSource: AccountPreferencesDataSource,
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao,
    private val deletedDao: DeletedAccountIdDao
) : LocalAccountRepository {

    /**
     * Сохраняет идентификатор текущего аккаунта, делегируя вызов [AccountPreferencesDataSource].
     */
    override suspend fun saveCurrentAccountId(id: Int) {
        accountPreferencesDataSource.saveAccountId(id = id)
    }

    /**
     * Получает сохранённый идентификатор текущего аккаунта, делегируя вызов [AccountPreferencesDataSource].
     */
    override suspend fun getCurrentAccountId(): Int? {
        return accountPreferencesDataSource.getAccountId()
    }

    override suspend fun getCurrentCurrency(): String {
        return accountPreferencesDataSource.getCurrentCurrency() ?: "RUB"
    }

    override suspend fun saveCurrency(currency: String) {
        accountPreferencesDataSource.saveCurrentCurrency(currency)
    }

    override suspend fun getAllAccounts(): List<AccountEntity> {
        return accountDao.getAllAccounts()
    }

    override suspend fun insertAll(accounts: List<AccountEntity>) {
        accountDao.insertAll(accounts)
    }

    override suspend fun getAccountById(id: Int): AccountEntity? {
        return accountDao.getAccountById(id)
    }

    override suspend fun insertAccount(account: AccountEntity) {
        accountDao.insertAccount(account)
    }

    override suspend fun upsertAll(accounts: List<AccountEntity>) {
        accountDao.upsertAll(accounts)
    }

    override suspend fun deleteAccount(id: Int): Boolean {
        val account = accountDao.getAccountById(id)
        if (account != null) {
            accountDao.delete(account)
            return true
        }
        return false
    }

    override suspend fun getTransactionCountForAccount(id: Int): Int {
        return transactionDao.getTransactionCountForAccount(id)
    }

    override suspend fun saveDeletedAccountId(id: Int) {
        deletedDao.insert(DeletedAccountIdEntity(id))
    }

    override suspend fun getDeletedAccountIds(): List<Int> {
        return deletedDao.getAll().map { it.id }
    }

    override suspend fun removeDeletedId(id: Int) {
        deletedDao.remove(id)
    }

    override suspend fun updateAccount(account: AccountEntity) {
        accountDao.updateAccount(account)
    }

    override suspend fun getUnsyncedAccounts(): List<AccountEntity> {
        return accountDao.getUnsynced()
    }
}
