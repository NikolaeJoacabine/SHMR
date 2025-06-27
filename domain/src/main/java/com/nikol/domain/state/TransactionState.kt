package com.nikol.domain.state

import com.nikol.domain.model.Transaction

/**
 * Представляет состояние получения списка транзакций.
 */
sealed class TransactionState {

    /**
     * Успешное получение списка транзакций.
     * @param items список транзакций
     */
    data class Success(val items: List<Transaction>) : TransactionState()

    /**
     * Ошибка сети (например, отсутствует интернет).
     */
    data object NetworkError : TransactionState()

    /**
     * Ошибка при получении транзакций.
     * @param message сообщение об ошибке
     */
    data class Error(val message: String) : TransactionState()
}
