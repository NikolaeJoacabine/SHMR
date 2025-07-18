package com.nikol.transaction.screens.analysis.stateHoisting

import com.nikol.domain.model.Category
import com.nikol.domain.model.CurrencyType
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

sealed class AnalysisScreenState {

    data class Content(
        val analysisState: AnalysisState,
        val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
        val endDate: LocalDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
    ) : AnalysisScreenState()

}

sealed class AnalysisState {
    data object Loading : AnalysisState()
    data object Error : AnalysisState()
    data object Empty : AnalysisState()
    data class Content(
        val list: List<Category>,
        val totalSum: Int,
        val currencyType: CurrencyType
    ) : AnalysisState()
}
