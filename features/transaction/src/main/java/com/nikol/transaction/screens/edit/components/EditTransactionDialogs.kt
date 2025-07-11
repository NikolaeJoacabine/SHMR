package com.nikol.transaction.screens.edit.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenAction
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenState
import com.nikol.ui.customUiComponents.ShowDatePickerDialog
import com.nikol.ui.customUiComponents.ShowTimePickerDialog

@Composable
fun EditTransactionDialogs(
    state: EditTransactionScreenState,
    showDialogWithAmount: MutableState<Boolean>,
    showDataPicker: MutableState<Boolean>,
    showTimePicker: MutableState<Boolean>,
    showDialog: MutableState<Boolean>,
    dialogMessage: MutableState<String>,
    onAction: (EditTransactionScreenAction) -> Unit
) {
    if (showDialogWithAmount.value) {
        val amountText = remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showDialogWithAmount.value = false },
            title = { Text("Введите сумму") },
            text = {
                TextField(
                    value = amountText.value,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*\\.?\\d{0,2}$")))
                            amountText.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialogWithAmount.value = false
                    amountText.value.toIntOrNull()?.let {
                        onAction(EditTransactionScreenAction.EditAmount(it))
                    }
                }) { Text("ОК") }
            },
            dismissButton = {
                TextButton(onClick = { showDialogWithAmount.value = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    if (showDataPicker.value && state is EditTransactionScreenState.Content) {
        ShowDatePickerDialog(
            initialDate = state.transaction.dateTime.toLocalDate(),
            onDateSelected = {
                onAction(EditTransactionScreenAction.EditDate(it))
                showDataPicker.value = false
            },
            onDismiss = { showDataPicker.value = false }
        )
    }

    if (showTimePicker.value && state is EditTransactionScreenState.Content) {
        ShowTimePickerDialog(
            initialTime = state.transaction.dateTime.toLocalTime(),
            onTimeSelected = {
                onAction(EditTransactionScreenAction.EditTime(it))
                showTimePicker.value = false
            },
            onDismiss = { showTimePicker.value = false }
        )
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Ошибка") },
            text = { Text(dialogMessage.value) },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) { Text("ОК") }
            }
        )
    }
}
