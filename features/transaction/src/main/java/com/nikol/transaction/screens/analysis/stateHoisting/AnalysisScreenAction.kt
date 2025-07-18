package com.nikol.transaction.screens.analysis.stateHoisting

import android.icu.util.LocaleData
import java.time.LocalDate

sealed class AnalysisScreenAction {
    data object OnScreenEntered : AnalysisScreenAction()
    data object OnNavigateBack : AnalysisScreenAction()
    data object OnClickStartDate : AnalysisScreenAction()
    data object OnClickEndDate : AnalysisScreenAction()

    data class StartDeteEdit(val localDate: LocalDate) : AnalysisScreenAction()
    data class EndDateEdit(val localDate: LocalDate) : AnalysisScreenAction()
}
