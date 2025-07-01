package com.nikol.transaction.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.transaction.screens.history.component.ErrorDateDialog
import com.nikol.transaction.screens.history.component.HistoryContentSection
import com.nikol.transaction.screens.history.component.HistoryTopBar
import com.nikol.transaction.screens.history.component.ShowDatePickerDialog
import com.nikol.transaction.screens.history.stateHoisting.HistoryScreenAction
import com.nikol.transaction.screens.history.stateHoisting.HistoryScreenEffect
import com.nikol.transaction.screens.history.stateHoisting.HistoryScreenState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel,
    onNavigateAnalyticScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToEditTransactionScreen: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val showDatePicker = remember { mutableStateOf<Pair<LocalDate, Boolean>?>(null) }
    val showErrorDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HistoryScreenEffect.NavigateAnalyticScreen -> onNavigateAnalyticScreen()
                is HistoryScreenEffect.NavigateBack -> onNavigateBack()
                is HistoryScreenEffect.NavigateEditTransactionScreen -> onNavigateToEditTransactionScreen()
                is HistoryScreenEffect.OpenDatePicker -> {
                    showDatePicker.value = effect.initialDate to effect.isStart
                }

                is HistoryScreenEffect.OpenErrorDateDialog -> {
                    showErrorDialog.value = true
                }
            }
        }
    }

    Scaffold(
        topBar = { HistoryTopBar { viewModel.onAction(HistoryScreenAction.OnBackButtonClicked) } }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            if (state.value is HistoryScreenState.Content) {
                val content = state.value as HistoryScreenState.Content
                HistoryContentSection(
                    content = content,
                    onAction = { viewModel.onAction(it) }
                )
            }

            showDatePicker.value?.let { (initialDate, isStart) ->
                ShowDatePickerDialog(
                    initialDate = initialDate,
                    onDateSelected = {
                        viewModel.onAction(HistoryScreenAction.OnDateSelected(it, isStart))
                        showDatePicker.value = null
                    },
                    onDismiss = { showDatePicker.value = null }
                )
            }

            ErrorDateDialog(
                showDialog = showErrorDialog.value,
                onDismiss = { showErrorDialog.value = false }
            )
        }
    }
}
