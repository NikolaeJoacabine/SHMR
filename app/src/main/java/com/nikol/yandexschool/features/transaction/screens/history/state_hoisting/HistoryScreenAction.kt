package com.nikol.yandexschool.features.transaction.screens.history.state_hoisting

import java.time.LocalDate

sealed class HistoryScreenAction {
    object OnScreenEntered : HistoryScreenAction()
    data class OnTransactionClick(val id: String) : HistoryScreenAction()
    data object OnAnalyticsButtonClick : HistoryScreenAction()
    data object OnBackButtonClicked : HistoryScreenAction()
    data object OnClickRetryRequest : HistoryScreenAction()
    data object OnClickedStartDate : HistoryScreenAction()
    data object OnClickedEndDate : HistoryScreenAction()
    data class OnDateSelected(val date: LocalDate, val isStart: Boolean) : HistoryScreenAction()
}