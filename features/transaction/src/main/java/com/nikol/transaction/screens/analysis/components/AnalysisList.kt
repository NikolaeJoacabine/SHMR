package com.nikol.transaction.screens.analysis.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nikol.data.util.formater.formatAmount
import com.nikol.domain.model.Category
import com.nikol.domain.model.CurrencyType
import com.nikol.ui.customUiComponents.ChevronRight
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.EmojiIcon
import com.nikol.ui.formatter.formatPercent

@Composable
fun AnalysisList(
    list: List<Category>,
    currencyType: CurrencyType
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = list,
            key = { it.id }
        ) {
            CustomListItem(
                modifier = Modifier
                    .height(70.dp)
                    .padding(horizontal = 16.dp),
                lead = {
                    EmojiIcon(it.emoji ?: "")
                },
                content = {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                trailing = {
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = it.percent.formatPercent(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = formatAmount(it.amount.toString()) + " ${currencyType.str}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    ChevronRight()
                }
            )
        }
    }
}
