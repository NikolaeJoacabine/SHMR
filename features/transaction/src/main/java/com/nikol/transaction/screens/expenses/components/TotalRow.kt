package com.nikol.transaction.screens.expenses.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikol.domain.model.CurrencyType
import com.nikol.transaction.R
import com.nikol.ui.customUiComponents.CustomListItem

@Composable
fun TotalRow(
    total: String,
    currencyType: CurrencyType
) {
    CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp),
        content = {
            Text(stringResource(R.string.amount), style = MaterialTheme.typography.bodyLarge)
        },
        trailing = {
            Text("$total ${currencyType.str}", style = MaterialTheme.typography.bodyLarge)
        }
    )
}
