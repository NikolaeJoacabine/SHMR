package com.nikol.yandexschool.features.transaction.screens.income.state_hoisting

sealed class IncomeScreenEffect {

    data object NavigateToHistoryScreen : IncomeScreenEffect()
    data object NavigateToAddedScreen : IncomeScreenEffect()

}