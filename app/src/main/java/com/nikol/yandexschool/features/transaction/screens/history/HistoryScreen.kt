package com.nikol.yandexschool.features.transaction.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.data.util.formater.formatAmount
import com.nikol.yandexschool.R
import com.nikol.yandexschool.features.transaction.screens.history.component.ShowDatePickerDialog
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenAction
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenEffect
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenState
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryTransaction
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon
import com.nikol.yandexschool.ui.customUiComponents.NoInternet
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel,
    onNavigateAnalyticScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToEditTransactionScreen: () -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    var showDatePicker = remember { mutableStateOf<Pair<LocalDate, Boolean>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HistoryScreenEffect.NavigateAnalyticScreen -> onNavigateAnalyticScreen()
                is HistoryScreenEffect.NavigateBack -> onNavigateBack()
                is HistoryScreenEffect.NavigateEditTransactionScreen -> onNavigateToEditTransactionScreen()
                is HistoryScreenEffect.OpenDatePicker -> {
                    showDatePicker.value = effect.initialDate to effect.isStart
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.my_history)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onAction(HistoryScreenAction.OnBackButtonClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onAction(HistoryScreenAction.OnAnalyticsButtonClick) }) {
                        Icon(
                            painter = painterResource(R.drawable.analys),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            if (state.value is HistoryScreenState.Content) {
                val content = state.value as HistoryScreenState.Content

                CustomListItem(
                    modifier = Modifier
                        .height(56.dp)
                        .clickable { viewModel.onAction(HistoryScreenAction.OnClickedStartDate) }
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp),
                    content = { Text(stringResource(R.string.beginning)) },
                    trailing = {
                        Text(
                            content.startDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
                        )
                    }
                )
                CustomListItem(
                    modifier = Modifier
                        .height(56.dp)
                        .clickable { viewModel.onAction(HistoryScreenAction.OnClickedEndDate) }
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp),
                    content = { Text(stringResource(R.string.end)) },
                    trailing = {
                        Text(
                            content.endDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
                        )
                    }
                )

                when (val txState = content.historyTransaction) {
                    is HistoryTransaction.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is HistoryTransaction.Error -> {
                        Text(
                            text = "Ошибка загрузки",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    is HistoryTransaction.Empty -> {
                        Text(
                            text = "Нет транзакций",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    is HistoryTransaction.NoInternet -> {
                        NoInternet(onClick = { viewModel.onAction(HistoryScreenAction.OnClickRetryRequest) })
                    }

                    is HistoryTransaction.Content -> {
                        CustomListItem(
                            modifier = Modifier
                                .height(56.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 16.dp),
                            content = { Text(stringResource(R.string.amount)) },
                            trailing = { Text(formatAmount(txState.totalSum) + " ₽") }
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(
                                items = txState.transactions,
                                key = { it.id }
                            ) { transaction ->
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
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 1
                                            )
                                            transaction.comment?.let {
                                                Text(
                                                    text = it,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = 1
                                                )
                                            }
                                        }
                                    },
                                    trailing = {
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(
                                                text = transaction.amount + " ₽",
                                                style = MaterialTheme.typography.bodyLarge,
                                            )
                                            Text(
                                                text = transaction.createdAt,
                                                style = MaterialTheme.typography.bodyLarge,
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
                }

                showDatePicker.value?.let { (initialDate, isStart) ->
                    ShowDatePickerDialog(
                        initialDate = initialDate,
                        onDateSelected = { date ->
                            viewModel.onAction(HistoryScreenAction.OnDateSelected(date, isStart))
                            showDatePicker.value = null
                        },
                        onDismiss = {
                            showDatePicker.value = null
                        }
                    )
                }
            }
        }
    }
}
