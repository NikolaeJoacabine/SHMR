package com.nikol.transaction.screens.income.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikol.transaction.models.TransactionUi
import com.nikol.transaction.screens.income.stateHoisting.IncomeScreenAction
import com.nikol.ui.customUiComponents.CustomListItem

@Composable
fun IncomeTransactionsList(
    transactions: List<TransactionUi>,
    onAction: (IncomeScreenAction) -> Unit
) {
    LazyColumn {
        items(transactions, key = { it.id }) { transaction ->
            CustomListItem(
                modifier = Modifier
                    .height(70.dp)
                    .clickable {
                        onAction(IncomeScreenAction.IncomeClicked(transaction.id))
                    }
                    .padding(horizontal = 16.dp),
                content = {
                    Column {
                        Text(
                            text = transaction.category,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        transaction.comment?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                },
                trailing = {
                    Text(
                        text = "${transaction.amount} ₽",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            )
        }
    }
}
