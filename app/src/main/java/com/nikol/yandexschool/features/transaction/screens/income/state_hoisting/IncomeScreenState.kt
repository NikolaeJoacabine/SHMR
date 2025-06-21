package com.nikol.yandexschool.features.transaction.screens.income.state_hoisting

import com.nikol.domain.model.Transaction

sealed class IncomeScreenState {
    data object Loading : IncomeScreenState()
    data class Date(
        val list: List<Transaction>,
        val total: Int
    ) : IncomeScreenState()

    data object Empty : IncomeScreenState()
    data object Error : IncomeScreenState()
    data object NoInternet : IncomeScreenState()
}