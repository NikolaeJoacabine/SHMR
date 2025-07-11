package com.nikol.transaction.screens.add

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.transaction.screens.add.components.AddTransactionBottomSheets
import com.nikol.transaction.screens.add.components.AddTransactionDialogs
import com.nikol.transaction.screens.add.components.AddTransactionScaffold
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenEffect
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenState
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTransactionScreen(
    viewModel: AddTransactionScreenViewModel,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val showBottomSheetAccount = remember { mutableStateOf(false) }
    val showBottomSheetArticles = remember { mutableStateOf(false) }
    val showAmountDialog = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }
    val selectedInitialDate = remember { mutableStateOf(LocalDate.now()) }
    val showTimePicker = remember { mutableStateOf(false) }
    val initialTime = remember { mutableStateOf(LocalTime.now()) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddTransactionScreenEffect.OnBack -> onNavigateBack()
                is AddTransactionScreenEffect.OnSuccessfulAddedTransaction -> {
                    Toast.makeText(context, "Транзакция добавлена", Toast.LENGTH_SHORT).show()
                    onNavigateBack()
                }
                is AddTransactionScreenEffect.OpenModalBottomSheetsWithAllAccount -> showBottomSheetAccount.value = true
                is AddTransactionScreenEffect.OpenModalBottomSheetsWithAllArticles -> showBottomSheetArticles.value = true
                is AddTransactionScreenEffect.CloseModalBottomSheetsWithAllAccount -> showBottomSheetAccount.value = false
                is AddTransactionScreenEffect.CloseModalBottomSheetsWithAllArticles -> showBottomSheetArticles.value = false
                is AddTransactionScreenEffect.CloseAmountDialog -> showAmountDialog.value = false
                is AddTransactionScreenEffect.OpenAmountDialog -> showAmountDialog.value = true
                is AddTransactionScreenEffect.OpenDatePickerDialog -> {
                    val currentState = viewModel.state.value
                    if (currentState is AddTransactionScreenState.Content) {
                        showDatePicker.value = true
                        selectedInitialDate.value = currentState.transaction.dateTime.toLocalDate()
                    }
                }
                is AddTransactionScreenEffect.OpenTimePickerDialog -> {
                    val currentState = viewModel.state.value
                    if (currentState is AddTransactionScreenState.Content) {
                        showTimePicker.value = true
                        initialTime.value = currentState.transaction.dateTime.toLocalTime()
                    }
                }
                is AddTransactionScreenEffect.OnShowNotFoundDialog -> {
                    dialogMessage.value = "Счёт или категория не найдены"
                    showDialog.value = true
                }
            }
        }
    }

    AddTransactionScaffold(
        state = state.value,
        onAction = viewModel::onAction
    )

    AddTransactionBottomSheets(
        state = state.value,
        showBottomSheetAccount = showBottomSheetAccount,
        showBottomSheetArticles = showBottomSheetArticles,
        onAction = viewModel::onAction
    )

    AddTransactionDialogs(
        showAmountDialog = showAmountDialog,
        showDatePicker = showDatePicker,
        selectedInitialDate = selectedInitialDate,
        showTimePicker = showTimePicker,
        initialTime = initialTime,
        showDialog = showDialog,
        dialogMessage = dialogMessage,
        onAction = viewModel::onAction
    )
}
