package com.nikol.yandexschool.features.transaction.screens.income.state_hoisting

sealed class IncomeScreenAction {
    data object OnScreenEntered : IncomeScreenAction()
    data object OnFloatingActionButtonClicked : IncomeScreenAction()
    data object OnHistoryButtonClicked : IncomeScreenAction()
    data object OnClickRetryRequest : IncomeScreenAction()
}