package com.nikol.yandexschool.features.transaction.screens.history.state_hoisting

import com.nikol.domain.model.Transaction
import java.time.LocalDate

sealed class HistoryScreenState {

    data class Content(
        val historyTransaction: HistoryTransaction,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : HistoryScreenState()
}

sealed class HistoryTransaction {
    object Loading : HistoryTransaction()
    object Error : HistoryTransaction()
    object Empty : HistoryTransaction()
    object NoInternet : HistoryTransaction()

    data class Content(
        val transactions: List<Transaction>,
        val totalSum: String
    ) : HistoryTransaction()
}
