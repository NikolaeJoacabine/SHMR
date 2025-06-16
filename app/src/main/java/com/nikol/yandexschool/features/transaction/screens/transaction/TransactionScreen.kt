package com.nikol.yandexschool.features.transaction.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.GetTransactionsUseCase
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon
import com.nikol.yandexschool.ui.customUiComponents.TransactionTopBar

@Composable
fun TransactionScreen(
    transactionType: TransactionType,
    viewModel: TransactionViewModel = TransactionViewModel(GetTransactionsUseCase()) //это временная мера пока без di
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(transactionType) {
        viewModel.loadTransactions(transactionType)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TransactionTopBar( if (transactionType == TransactionType.Expenses) "Расходы" else "Доходы") { }
        }
    ) {

        when (state.value) {
            is TransactionState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is TransactionState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ошибка загрузки")
                }
            }

            is TransactionState.Success -> {
                val listItem = (state.value as TransactionState.Success).items
                Column(
                    modifier = Modifier.fillMaxSize().padding(it)
                ) {
                    CustomListItem(
                        modifier = Modifier
                            .height(56.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 16.dp),
                        content = {
                            Text(
                                text = "Всего",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailing = {
                            val total = listItem.sumOf { it.amount.replace(" ", "").toInt() }
                            Text(text = "$total ₽")
                        }
                    )
                    LazyColumn {
                        items(listItem, key = { it.id }) { item ->
                            CustomListItem(
                                modifier = Modifier
                                    .height(70.dp)
                                    .clickable { }
                                    .padding(horizontal = 16.dp),
                                lead = item.emoji?.let { emoji ->
                                    { EmojiIcon(emoji) }
                                },
                                content = {
                                    Column {
                                        Text(
                                            text = item.category,
                                            maxLines = 1,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        item.comment?.let { comment ->
                                            Text(
                                                text = comment,
                                                maxLines = 1,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                },
                                trailing = {
                                    Text(
                                        text = item.amount + " ₽",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowRight,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary.copy(0.3f)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
