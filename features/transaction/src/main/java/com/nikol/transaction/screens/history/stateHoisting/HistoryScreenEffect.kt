package com.nikol.transaction.screens.history.stateHoisting

import java.time.LocalDate

/**
 * Эффекты (однократные события) для экрана истории транзакций,
 * которые вызывают навигацию или показывают диалоги.
 */
sealed class HistoryScreenEffect {

    /** Навигация назад. */
    data object NavigateBack : HistoryScreenEffect()

    /** Навигация на экран редактирования транзакции. */
    data class NavigateEditTransactionScreen(val id: Int) : HistoryScreenEffect()

    /** Навигация на экран аналитики. */
    object NavigateAnalyticScreen : HistoryScreenEffect()

    /**
     * Открыть диалог выбора даты.
     * @param initialDate начальная дата для отображения в диалоге
     * @param isStart true, если выбирается начальная дата, иначе конечная
     */
    data class OpenDatePicker(val initialDate: LocalDate, val isStart: Boolean) : HistoryScreenEffect()

    /** Показать диалог ошибки выбора даты. */
    object OpenErrorDateDialog : HistoryScreenEffect()
}
