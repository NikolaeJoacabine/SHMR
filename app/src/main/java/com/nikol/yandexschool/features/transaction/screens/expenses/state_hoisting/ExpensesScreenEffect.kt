package com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting

sealed interface ExpensesScreenEffect {
    data class NavigateToDetail(val transactionId: String) : ExpensesScreenEffect
    data object NavigateToHistory : ExpensesScreenEffect
    data object NavigateToAdded : ExpensesScreenEffect
}
