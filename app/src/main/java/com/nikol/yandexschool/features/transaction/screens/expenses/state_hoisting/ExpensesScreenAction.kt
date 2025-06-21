package com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting


sealed class ExpensesScreenAction {

    data object OnScreenEntered : ExpensesScreenAction()
    data class ExpensesClicked(val id: Int) : ExpensesScreenAction()
    data object FloatingActionButtonClicked : ExpensesScreenAction()
    data object OnHistoryClicked : ExpensesScreenAction()
    data object OnClickRetryRequest : ExpensesScreenAction()
}