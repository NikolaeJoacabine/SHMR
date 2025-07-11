package com.nikol.transaction.screens.add.components

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
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenAction
import com.nikol.ui.customUiComponents.ShowDatePickerDialog
import com.nikol.ui.customUiComponents.ShowTimePickerDialog
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun AddTransactionDialogs(
    showAmountDialog: MutableState<Boolean>,
    showDatePicker: MutableState<Boolean>,
    selectedInitialDate: MutableState<LocalDate>,
    showTimePicker: MutableState<Boolean>,
    initialTime: MutableState<LocalTime>,
    showDialog: MutableState<Boolean>,
    dialogMessage: MutableState<String>,
    onAction: (AddTransactionScreenAction) -> Unit
) {
    if (showAmountDialog.value) {
        val text = remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAmountDialog.value = false },
            title = { Text("Введите сумму") },
            text = {
                TextField(
                    value = text.value,
                    onValueChange = { new ->
                        if (new.matches(Regex("^\\d*\\.?\\d{0,2}$"))) text.value = new
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showAmountDialog.value = false
                    onAction(AddTransactionScreenAction.SelectAmount(text.value))
                }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAmountDialog.value = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    if (showDatePicker.value) {
        ShowDatePickerDialog(
            initialDate = selectedInitialDate.value,
            onDateSelected = { selected ->
                showDatePicker.value = false
                onAction(AddTransactionScreenAction.SelectDate(selected))
            },
            onDismiss = {
                showDatePicker.value = false
            }
        )
    }

    if (showTimePicker.value) {
        ShowTimePickerDialog(
            initialTime = initialTime.value,
            onTimeSelected = { time ->
                showTimePicker.value = false
                onAction(AddTransactionScreenAction.SelectTime(time))
            },
            onDismiss = {
                showTimePicker.value = false
            }
        )
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Ошибка") },
            text = { Text(dialogMessage.value) },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("ОК")
                }
            }
        )
    }
}
