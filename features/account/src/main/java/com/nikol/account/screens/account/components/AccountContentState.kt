package com.nikol.account.screens.account.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chartsui.barChart.Bar
import com.example.chartsui.barChart.BarChart
import com.nikol.account.screens.account.stateHoisting.AccountScreensState

@Composable
internal fun AccountContentState(
    state: AccountScreensState.Content,
    padding: PaddingValues,
    onClickCurrency: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.list, key = { it.id }) { account ->
                AccountListItem(account, state.currentCurrency)
            }
        }
        CurrencyListItem(onClick = onClickCurrency, currencyType = state.currentCurrency)

        val bars = state.listAnalysis.map {
            Bar(
                isNegative = it.isNegative,
                count = it.amount,
                date = it.date
            )
        }
        Spacer(Modifier.size(20.dp))
        BarChart(bars.take(8))

    }
}
