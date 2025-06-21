package com.nikol.yandexschool.features.transaction.screens.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.data.util.formater.formatAmount
import com.nikol.yandexschool.R
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenAction
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenAction.ExpensesClicked
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenEffect
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenState
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon
import com.nikol.yandexschool.ui.customUiComponents.NoInternet
import com.nikol.yandexschool.ui.customUiComponents.TransactionTopBar
import java.nio.file.WatchEvent

@Composable
fun ExpensesScreen(
    viewModel: ExpensesScreenViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToAdded: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ExpensesScreenEffect.NavigateToDetail -> onNavigateToDetail(effect.transactionId)
                is ExpensesScreenEffect.NavigateToAdded -> onNavigateToAdded()
                is ExpensesScreenEffect.NavigateToHistory -> onNavigateToHistory()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TransactionTopBar(
                title = stringResource(R.string.expenses),
                onClick = { onNavigateToHistory() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onIntent(ExpensesScreenAction.FloatingActionButtonClicked)
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        when (val currentState = state.value) {

            is ExpensesScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ExpensesScreenState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Уппс, что произошло на нашей стороне")
                }
            }

            is ExpensesScreenState.Content -> {
                val transactions = currentState.expenses
                val total = formatAmount(currentState.total.toString())
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding())
                ) {
                    CustomListItem(
                        modifier = Modifier
                            .height(56.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 16.dp),
                        content = {
                            Text("Всего", style = MaterialTheme.typography.bodyLarge)
                        },
                        trailing = {
                            Text("$total ₽")
                        }
                    )

                    LazyColumn {
                        items(transactions, key = { it.id }) { transaction ->
                            CustomListItem(
                                modifier = Modifier
                                    .height(70.dp)
                                    .clickable {
                                        viewModel.onIntent(
                                            ExpensesClicked(
                                                transaction.id
                                            )
                                        )
                                    }
                                    .padding(horizontal = 16.dp),
                                lead = transaction.emoji?.let { { EmojiIcon(it) } },
                                content = {
                                    Column {
                                        Text(
                                            transaction.category,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        transaction.comment?.let {
                                            Text(
                                                it,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                },
                                trailing = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "${transaction.amount} ₽",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Icon(
                                            Icons.Default.KeyboardArrowRight,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )

                        }
                    }
                }
            }

            is ExpensesScreenState.EmptyList -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.emty_expenses))
                }
            }

            is ExpensesScreenState.NoInternet -> {
                NoInternet(
                    onClick = {
                        viewModel.onIntent(ExpensesScreenAction.OnClickRetryRequest)
                    },
                    modifier = Modifier.padding(top = padding.calculateTopPadding())
                )
            }
        }
    }
}