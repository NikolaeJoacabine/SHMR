package com.nikol.transaction.screens.add.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nikol.transaction.screens.add.stateHoisting.AccountsState
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenAction
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenState
import com.nikol.transaction.screens.add.stateHoisting.ArticlesState
import com.nikol.ui.customUiComponents.ChevronRight
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.EmojiIcon
import com.nikol.ui.utils.formatAmountToUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTransactionBottomSheets(
    state: AddTransactionScreenState,
    showBottomSheetAccount: MutableState<Boolean>,
    showBottomSheetArticles: MutableState<Boolean>,
    onAction: (AddTransactionScreenAction) -> Unit
) {
    if (showBottomSheetAccount.value) {
        ModalBottomSheet(onDismissRequest = { showBottomSheetAccount.value = false }) {
            val accountsState = (state as? AddTransactionScreenState.Content)?.accounts
            when (accountsState) {
                is AccountsState.Content -> LazyColumn {
                    items(accountsState.list, key = { it.id }) { account ->
                        CustomListItem(
                            modifier = Modifier
                                .height(57.dp)
                                .clickable {
                                    onAction(
                                        AddTransactionScreenAction.SelectAccount(
                                            name = account.name,
                                            id = account.id
                                        )
                                    )
                                }
                                .padding(horizontal = 16.dp),
                            lead = { EmojiIcon(account.emoji, Color.White) },
                            content = {
                                Text(
                                    text = account.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            trailing = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = formatAmountToUi(account.balance),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    ChevronRight()
                                }
                            }
                        )
                    }
                }
                is AccountsState.Error -> ErrorContent(message = "Ошибка загрузки счетов. Попробуйте позже.")
                is AccountsState.Loading -> Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                is AccountsState.NoInternet -> ErrorContent(
                    message = "Нет подключения к интернету. Проверьте соединение."
                )
                else -> {}
            }
        }
    }

    if (showBottomSheetArticles.value) {
        ModalBottomSheet(onDismissRequest = { showBottomSheetArticles.value = false }) {
            val articlesState = (state as? AddTransactionScreenState.Content)?.articles
            when (articlesState) {
                is ArticlesState.Content -> LazyColumn {
                    items(articlesState.articles, key = { it.id }) { article ->
                        CustomListItem(
                            modifier = Modifier
                                .height(68.dp)
                                .clickable {
                                    onAction(
                                        AddTransactionScreenAction.SelectArticles(
                                            name = article.name,
                                            id = article.id
                                        )
                                    )
                                }
                                .padding(horizontal = 16.dp),
                            lead = { EmojiIcon(article.emoji) },
                            content = { Text(text = article.name) }
                        )
                    }
                }
                is ArticlesState.Error -> ErrorContent(message = "Ошибка загрузки категорий. Попробуйте позже.")
                is ArticlesState.Loading -> Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                is ArticlesState.NoInternet -> ErrorContent(
                    message = "Нет подключения к интернету. Проверьте соединение."
                )
                else -> {}
            }
        }
    }
}

@Composable
private fun ErrorContent(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
