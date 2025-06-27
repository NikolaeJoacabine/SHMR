package com.nikol.yandexschool.features.transaction.screens.history.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikol.yandexschool.R
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryScreenAction
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryScreenState
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryTransaction
import com.nikol.yandexschool.ui.customUiComponents.NoInternet

@Composable
fun HistoryContentSection(
    content: HistoryScreenState.Content,
    onAction: (HistoryScreenAction) -> Unit
) {
    HistoryDateItem(
        title = stringResource(R.string.beginning),
        date = content.startDate,
        onClick = { onAction(HistoryScreenAction.OnClickedStartDate) }
    )
    HistoryDateItem(
        title = stringResource(R.string.end),
        date = content.endDate,
        onClick = { onAction(HistoryScreenAction.OnClickedEndDate) }
    )

    when (val txState = content.historyTransaction) {
        is HistoryTransaction.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }

        is HistoryTransaction.Error ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Ошибка загрузки",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }

        is HistoryTransaction.Empty ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Нет транзакций",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        is HistoryTransaction.NoInternet -> NoInternet(
            onClick = {
                onAction(HistoryScreenAction.OnClickRetryRequest)
            }
        )

        is HistoryTransaction.Content -> {
            HistoryTotalAmount(txState.totalSum)
            HistoryList(transactions = txState.transactions)
        }
    }
}
