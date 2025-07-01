package com.nikol.transaction.screens.expenses

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.transaction.R
import com.nikol.transaction.screens.expenses.components.ExpensesScreenContent
import com.nikol.transaction.screens.expenses.stateHoisting.ExpensesScreenAction
import com.nikol.transaction.screens.expenses.stateHoisting.ExpensesScreenEffect
import com.nikol.ui.customUiComponents.TransactionTopBar

@OptIn(ExperimentalMaterial3Api::class)
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
                onClick = { viewModel.onIntent(ExpensesScreenAction.OnHistoryClicked) }
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
        ExpensesScreenContent(
            state = state.value,
            onAction = viewModel::onIntent,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        )
    }
}
