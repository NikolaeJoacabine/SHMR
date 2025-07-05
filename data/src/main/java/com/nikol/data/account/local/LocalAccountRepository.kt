package com.nikol.data.account.local

import android.icu.util.Currency

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
}
