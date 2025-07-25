package com.nikol.transaction.screens.expenses.components

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
import com.nikol.transaction.screens.expenses.stateHoisting.ExpensesScreenAction
import com.nikol.transaction.screens.expenses.stateHoisting.ExpensesScreenState
import com.nikol.ui.customUiComponents.NoInternet

@Composable
fun ExpensesScreenContent(
    state: ExpensesScreenState,
    onAction: (ExpensesScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is ExpensesScreenState.Content -> {
            Column(modifier = modifier) {
                TotalRow(
                    total = formatAmount(state.total.toString()),
                    state.currencyType
                )
                TransactionsList(
                    transactions = state.expenses,
                    onAction = onAction,
                    currencyType = state.currencyType
                )
            }
        }

        is ExpensesScreenState.EmptyList -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.empty_expenses))
            }
        }

        is ExpensesScreenState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text("Упс, что-то пошло не так 😥")
            }
        }

        is ExpensesScreenState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ExpensesScreenState.NoInternet -> {
            NoInternet(onClick = {
                onAction(ExpensesScreenAction.OnClickRetryRequest)
            }, modifier = modifier)
        }
    }
}
