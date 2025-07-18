package com.nikol.transaction.screens.income.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikol.data.util.formater.formatAmount
import com.nikol.transaction.R
import com.nikol.transaction.screens.income.stateHoisting.IncomeScreenAction
import com.nikol.transaction.screens.income.stateHoisting.IncomeScreenState
import com.nikol.ui.customUiComponents.NoInternet

@Composable
fun IncomeScreenContent(
    state: IncomeScreenState,
    onAction: (IncomeScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is IncomeScreenState.Date -> {
            Column(modifier = modifier) {
                IncomeTotalRow(
                    total = formatAmount(state.total.toString()),
                    currencyType = state.currencyType
                )
                IncomeTransactionsList(
                    transactions = state.list,
                    onAction = onAction,
                    currencyType = state.currencyType
                )
            }
        }

        is IncomeScreenState.Empty -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.empty_income))
            }
        }

        is IncomeScreenState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text("Ошибка загрузки")
            }
        }

        is IncomeScreenState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is IncomeScreenState.NoInternet -> {
            NoInternet(
                onClick = { onAction(IncomeScreenAction.OnClickRetryRequest) },
                modifier = modifier
            )
        }
    }
}
