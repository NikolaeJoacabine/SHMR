package com.nikol.yandexschool.features.transaction.screens.history.state_hoisting

import java.time.LocalDate

sealed class HistoryScreenEffect {
    object NavigateBack : HistoryScreenEffect()
    object NavigateEditTransactionScreen : HistoryScreenEffect()
    object NavigateAnalyticScreen : HistoryScreenEffect()
    data class OpenDatePicker(val initialDate: LocalDate, val isStart: Boolean) : HistoryScreenEffect()
}