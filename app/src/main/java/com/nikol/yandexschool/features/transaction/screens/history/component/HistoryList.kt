package com.nikol.yandexschool.features.transaction.screens.history.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nikol.yandexschool.features.transaction.models.TransactionHistoryUi
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon

@Composable
fun HistoryList(transactions: List<TransactionHistoryUi>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = transactions, key = { it.id }) { transaction ->
            CustomListItem(
                modifier = Modifier
                    .height(70.dp)
                    .padding(horizontal = 16.dp),
                lead = { EmojiIcon(transaction.emoji.toString()) },
                content = {
                    Column {
                        Text(
                            text = transaction.category,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        transaction.comment?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                trailing = {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = transaction.amount + " ₽",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = transaction.createdAt, style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary.copy(0.3f)
                    )
                }
            )
        }
    }
}
