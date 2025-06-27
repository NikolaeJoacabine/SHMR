package com.nikol.data.account.local

/**
 * Реализация [LocalAccountRepository], использующая [AccountPreferencesDataSource]
 * для сохранения и получения текущего идентификатора аккаунта.
 */
class LocalAccountRepositoryImpl(
    private val accountPreferencesDataSource: AccountPreferencesDataSource
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
}
