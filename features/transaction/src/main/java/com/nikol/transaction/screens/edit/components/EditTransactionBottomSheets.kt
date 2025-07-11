package com.nikol.transaction.screens.edit.components

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
import androidx.compose.ui.unit.dp
import com.nikol.transaction.screens.edit.stateHoisting.AccountsStateEdit
import com.nikol.transaction.screens.edit.stateHoisting.ArticlesStateEdit
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenAction
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenState
import com.nikol.ui.customUiComponents.ChevronRight
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.EmojiIcon
import com.nikol.ui.utils.formatAmountToUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditTransactionBottomSheets(
    state: EditTransactionScreenState,
    showBottomSheetWithAccount: MutableState<Boolean>,
    showBottomSheetWithArticles: MutableState<Boolean>,
    onAction: (EditTransactionScreenAction) -> Unit
) {
    if (state !is EditTransactionScreenState.Content) return

    if (showBottomSheetWithAccount.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetWithAccount.value = false }
        ) {
            when (val accountState = state.accountsStateEdit) {
                is AccountsStateEdit.Content -> {
                    LazyColumn {
                        items(accountState.list, key = { it.id }) { account ->
                            CustomListItem(
                                modifier = Modifier
                                    .height(57.dp)
                                    .clickable {
                                        onAction(EditTransactionScreenAction.EditAccount(account))
                                    }
                                    .padding(horizontal = 16.dp),
                                lead = { EmojiIcon(account.emoji, Color.White) },
                                content = { Text(account.name, style = MaterialTheme.typography.bodyLarge) },
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
                }

                is AccountsStateEdit.Loading -> Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is AccountsStateEdit.Error -> {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Ошибка при загрузке счетов")
                    }
                }
            }
        }
    }

    if (showBottomSheetWithArticles.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetWithArticles.value = false }
        ) {
            when (val articleState = state.articlesStateEdit) {
                is ArticlesStateEdit.Content -> {
                    LazyColumn {
                        items(articleState.articles, key = { it.id }) { article ->
                            CustomListItem(
                                modifier = Modifier
                                    .height(68.dp)
                                    .clickable {
                                        onAction(EditTransactionScreenAction.EditArticles(article))
                                    }
                                    .padding(horizontal = 16.dp),
                                lead = { EmojiIcon(article.emoji) },
                                content = { Text(article.name) }
                            )
                        }
                    }
                }

                is ArticlesStateEdit.Loading -> Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is ArticlesStateEdit.Error -> {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Ошибка при загрузке категорий")
                    }
                }
            }
        }
    }
}
