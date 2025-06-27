package com.nikol.yandexschool.features.transaction.screens.expenses.stateHoisting

/**
 * Побочные эффекты (Effects) для экрана расходов, которые влияют на навигацию.
 */
sealed interface ExpensesScreenEffect {

    /** Переход к деталям транзакции с указанным ID. */
    data class NavigateToDetail(val transactionId: String) : ExpensesScreenEffect

    /** Переход на экран истории расходов. */
    data object NavigateToHistory : ExpensesScreenEffect

    /** Переход на экран добавления новой транзакции. */
    data object NavigateToAdded : ExpensesScreenEffect
}
