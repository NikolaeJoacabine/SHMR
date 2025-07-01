package com.nikol.transaction.screens.income

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.nikol.transaction.screens.income.component.IncomeScreenContent
import com.nikol.transaction.screens.income.stateHoisting.IncomeScreenAction
import com.nikol.transaction.screens.income.stateHoisting.IncomeScreenEffect
import com.nikol.ui.customUiComponents.TransactionTopBar

@Composable
fun IncomeScreen(
    viewModel: IncomeScreenViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToAdded: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is IncomeScreenEffect.NavigateToAddedScreen -> onNavigateToAdded()
                is IncomeScreenEffect.NavigateToHistoryScreen -> onNavigateToHistory()
                is IncomeScreenEffect.NavigateToDetail -> onNavigateToDetail(effect.transactionId)
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
        IncomeScreenContent(
            state = state.value,
            onAction = viewModel::onIntent,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        )
    }
}
