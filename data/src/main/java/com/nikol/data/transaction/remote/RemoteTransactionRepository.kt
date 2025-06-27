package com.nikol.data.transaction.remote

import com.nikol.domain.state.TransactionState
import java.time.LocalDate

/**
 * Репозиторий для получения транзакций с удалённого сервера.
 */
interface RemoteTransactionRepository {

    /**
     * Получает транзакции за сегодняшний день по идентификатору аккаунта.
     *
     * @param id Идентификатор аккаунта.
     * @return Состояние загрузки транзакций ([TransactionState]).
     */
    suspend fun transactionForToday(id: Int): TransactionState

    /**
     * Получает транзакции за указанный период по идентификатору аккаунта.
     *
     * @param id Идентификатор аккаунта.
     * @param startDate Начальная дата периода.
     * @param endDate Конечная дата периода.
     * @return Состояние загрузки транзакций ([TransactionState]).
     */
    suspend fun getTransactionsByPeriod(
        id: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState
}

