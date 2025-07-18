package com.nikol.transaction.screens.analysis.stateHoisting

sealed class AnalysisScreenEffect {
    data object NavigateBack : AnalysisScreenEffect()
    data object OpenStartDateEditDialog : AnalysisScreenEffect()
    data object OpenEndDateEditDialog : AnalysisScreenEffect()
    data object OpenErrorDialog : AnalysisScreenEffect()
}
