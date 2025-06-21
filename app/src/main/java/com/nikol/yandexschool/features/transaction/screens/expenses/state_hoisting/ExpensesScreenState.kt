package com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting

import com.nikol.domain.model.Transaction

sealed class ExpensesScreenState {
    data object Loading : ExpensesScreenState()
    data class Content(
        val expenses: List<Transaction>,
        val total: Int
    ) : ExpensesScreenState()

    data object EmptyList : ExpensesScreenState()
    data object Error : ExpensesScreenState()
    data object NoInternet : ExpensesScreenState()
}