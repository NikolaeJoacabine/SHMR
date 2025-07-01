package com.nikol.transaction.screens.history.stateHoisting

import com.nikol.transaction.models.TransactionHistoryUi
import java.time.LocalDate

/**
 * Состояния экрана истории транзакций.
 */
sealed class HistoryScreenState {

    /**
     * Содержимое экрана с историей транзакций.
     *
     * @param historyTransaction текущее состояние списка транзакций (загрузка, ошибка, данные и т.п.)
     * @param startDate выбранная начальная дата фильтра
     * @param endDate выбранная конечная дата фильтра
     */
    data class Content(
        val historyTransaction: HistoryTransaction,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : HistoryScreenState()
}

/**
 * Состояния загрузки/отображения списка транзакций на экране истории.
 */
sealed class HistoryTransaction {
    /** Состояние загрузки данных. */
    object Loading : HistoryTransaction()

    /** Состояние ошибки при загрузке данных. */
    object Error : HistoryTransaction()

    /** Состояние, когда нет транзакций для отображения. */
    object Empty : HistoryTransaction()

    /** Состояние отсутствия подключения к интернету. */
    object NoInternet : HistoryTransaction()

    /**
     * Состояние с загруженным списком транзакций.
     *
     * @param transactions список транзакций для отображения
     * @param totalSum итоговая сумма по транзакциям, отформатированная для UI
     */
    data class Content(
        val transactions: List<TransactionHistoryUi>,
        val totalSum: String
    ) : HistoryTransaction()
}
