package com.nikol.transaction.screens.expenses.stateHoisting


/**
 * Действия (Actions) пользователя на экране расходов.
 */
sealed class ExpensesScreenAction {

    /** Экран был открыт пользователем. */
    data object OnScreenEntered : ExpensesScreenAction()

    /** Пользователь кликнул по расходу с указанным ID. */
    data class ExpensesClicked(val id: Int) : ExpensesScreenAction()

    /** Пользователь нажал на плавающую кнопку действия (FAB). */
    data object FloatingActionButtonClicked : ExpensesScreenAction()

    /** Пользователь нажал на кнопку перехода в историю расходов. */
    data object OnHistoryClicked : ExpensesScreenAction()

    /** Пользователь нажал кнопку повторной попытки запроса данных. */
    data object OnClickRetryRequest : ExpensesScreenAction()
}
