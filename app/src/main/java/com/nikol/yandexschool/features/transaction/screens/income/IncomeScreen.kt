package com.nikol.yandexschool.features.transaction.screens.income

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.data.util.formater.formatAmount
import com.nikol.yandexschool.R
import com.nikol.yandexschool.features.transaction.screens.income.state_hoisting.IncomeScreenAction
import com.nikol.yandexschool.features.transaction.screens.income.state_hoisting.IncomeScreenEffect
import com.nikol.yandexschool.features.transaction.screens.income.state_hoisting.IncomeScreenState
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.NoInternet
import com.nikol.yandexschool.ui.customUiComponents.TransactionTopBar

@Composable
fun IncomeScreen(
    viewModel: IncomeScreenViewModel,
    onNavigateToDetail: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToAdded: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is IncomeScreenEffect.NavigateToAddedScreen -> onNavigateToAdded()
                is IncomeScreenEffect.NavigateToHistoryScreen -> onNavigateToHistory()
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TransactionTopBar(stringResource(R.string.income)) {
                viewModel.onIntent(IncomeScreenAction.OnHistoryButtonClicked)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onIntent(IncomeScreenAction.OnFloatingActionButtonClicked)
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        when (val currentState = state.value) {

            is IncomeScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is IncomeScreenState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ошибка загрузки")
                }
            }

            is IncomeScreenState.Date -> {
                val transactions = currentState.list
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

                                    }
                                    .padding(horizontal = 16.dp),
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

            is IncomeScreenState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.emty_income))
                }
            }

            is IncomeScreenState.NoInternet -> {
                NoInternet(
                    onClick = { viewModel.onIntent(IncomeScreenAction.OnClickRetryRequest) },
                    modifier = Modifier.padding(top = padding.calculateTopPadding())
                )
            }
        }
    }
}