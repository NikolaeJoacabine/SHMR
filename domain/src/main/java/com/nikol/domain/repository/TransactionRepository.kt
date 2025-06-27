package com.nikol.domain.repository

import com.nikol.domain.state.TransactionState
import java.time.LocalDate

/**
 * Интерфейс репозитория для получения транзакций пользователя.
 * Определяет методы для получения транзакций за сегодняшний день
 * и за произвольный период времени.
 */
interface TransactionRepository {

    /**
     * Получает транзакции за сегодняшний день для указанного аккаунта.
     *
     * @param id Идентификатор аккаунта.
     * @return Состояние результата загрузки транзакций.
     */
    suspend fun getTransactionsForToday(id: Int): TransactionState

    /**
     * Получает транзакции за указанный период для указанного аккаунта.
     *
     * @param id Идентификатор аккаунта.
     * @param startDate Начальная дата периода.
     * @param endDate Конечная дата периода.
     * @return Состояние результата загрузки транзакций.
     */
    suspend fun getTransactionsByPeriod(
        id: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState
}
