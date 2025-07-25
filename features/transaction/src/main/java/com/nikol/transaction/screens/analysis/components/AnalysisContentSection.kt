package com.nikol.transaction.screens.analysis.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikol.transaction.R
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenAction
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenState
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisState

@Composable
internal fun AnalysisContentSection(
    content: AnalysisScreenState.Content,
    onAction: (AnalysisScreenAction) -> Unit
) {
    AnalysisDateItem(
        title = stringResource(R.string.beginning),
        date = content.startDate,
        onClick = { onAction(AnalysisScreenAction.OnClickStartDate) }
    )
    AnalysisDateItem(
        title = stringResource(R.string.end),
        date = content.endDate,
        onClick = { onAction(AnalysisScreenAction.OnClickEndDate) }
    )

    when (val state = content.analysisState) {
        is AnalysisState.Content -> {
            AnalysisTotalAmount(
                amount = state.totalSum.toString(),
                currencyType = state.currencyType
            )
            AnalysisList(
                list = state.list,
                currencyType = state.currencyType
            )
        }

        is AnalysisState.Empty ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Нет транзакций",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        is AnalysisState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Ошибка загрузки",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is AnalysisState.Loading ->
            Box(
                Modifier.fillMaxSize(),
                Alignment.Center
            ) {
                CircularProgressIndicator()
            }
    }
}