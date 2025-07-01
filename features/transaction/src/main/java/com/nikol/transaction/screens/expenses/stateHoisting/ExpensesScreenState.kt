package com.nikol.transaction.screens.expenses.stateHoisting

import com.nikol.transaction.models.TransactionUi

/**
 * Состояния экрана расходов.
 */
sealed class ExpensesScreenState {

    /** Состояние загрузки данных. */
    data object Loading : ExpensesScreenState()

    /**
     * Состояние с загруженными расходами.
     * @param expenses список транзакций расходов
     * @param total общая сумма расходов
     */
    data class Content(
        val expenses: List<TransactionUi>,
        val total: Int
    ) : ExpensesScreenState()

    /** Состояние, когда список расходов пуст. */
    data object EmptyList : ExpensesScreenState()

    /** Состояние ошибки при загрузке данных. */
    data object Error : ExpensesScreenState()

    /** Состояние отсутствия интернет-соединения. */
    data object NoInternet : ExpensesScreenState()
}
