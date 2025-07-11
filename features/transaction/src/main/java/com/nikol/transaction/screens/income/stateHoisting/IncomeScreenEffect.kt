package com.nikol.transaction.screens.income.stateHoisting

/**
 * Эффекты (одноразовые события) экрана доходов, которые не хранятся в состоянии,
 * но приводят к навигации или другим побочным действиям.
 */
sealed class IncomeScreenEffect {

    /** Эффект для перехода на экран истории доходов. */
    data object NavigateToHistoryScreen : IncomeScreenEffect()

    /** Эффект для перехода на экран добавления нового дохода. */
    data object NavigateToAddedScreen : IncomeScreenEffect()

    /**
     * Эффект для перехода на экран подробной информации о транзакции.
     *
     * @param transactionId идентификатор выбранной транзакции
     */
    data class NavigateToDetail(val transactionId: Int) : IncomeScreenEffect()
}
