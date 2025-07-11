package com.nikol.transaction.screens.edit

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.transaction.R
import com.nikol.transaction.screens.edit.components.EditTransactionBottomSheets
import com.nikol.transaction.screens.edit.components.EditTransactionDialogs
import com.nikol.transaction.screens.edit.components.EditTransactionScaffold
import com.nikol.transaction.screens.edit.stateHoisting.AccountsStateEdit
import com.nikol.transaction.screens.edit.stateHoisting.ArticlesStateEdit
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenAction
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenEffect
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenState
import com.nikol.ui.customUiComponents.ChevronRight
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.EmojiIcon
import com.nikol.ui.customUiComponents.ShowDatePickerDialog
import com.nikol.ui.customUiComponents.ShowTimePickerDialog
import com.nikol.ui.utils.formatAmountToUi
import java.time.format.DateTimeFormatter

@Composable
internal fun EditTransactionScreen(
    viewModel: EditTransactionScreenViewModel,
    navigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val showBottomSheetWithAccount = remember { mutableStateOf(false) }
    val showBottomSheetWithArticles = remember { mutableStateOf(false) }
    val showDialogWithAmount = remember { mutableStateOf(false) }
    val showDataPicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is EditTransactionScreenEffect.NavigateBack -> navigateBack()
                is EditTransactionScreenEffect.OpenDialogWithAmount -> showDialogWithAmount.value = true
                is EditTransactionScreenEffect.OpenModalBottomSheetWithAccounts -> showBottomSheetWithAccount.value = true
                is EditTransactionScreenEffect.OpenModalBottomSheetWithArticles -> showBottomSheetWithArticles.value = true
                is EditTransactionScreenEffect.OpenPickDateDialog -> showDataPicker.value = true
                is EditTransactionScreenEffect.OpenPickTimeDialog -> showTimePicker.value = true
                is EditTransactionScreenEffect.CloseModalBottomSheetWithAccount -> showBottomSheetWithAccount.value = false
                is EditTransactionScreenEffect.CloseModalBottomSheetWithArticles -> showBottomSheetWithArticles.value = false
                is EditTransactionScreenEffect.SuccessfulUpdate -> {
                    Toast.makeText(context, "Транзакция обновлена", Toast.LENGTH_SHORT).show()
                    navigateBack()
                }
                is EditTransactionScreenEffect.ShowNotFoundDialog -> {
                    showDialog.value = true
                    dialogMessage.value = "Счёт или транзакция не найдены"
                }
                is EditTransactionScreenEffect.SuccessfulDelete -> {
                    Toast.makeText(context, "Транзакция удалена", Toast.LENGTH_SHORT).show()
                    navigateBack()
                }
            }
        }
    }

    EditTransactionScaffold(
        state = state.value,
        onAction = viewModel::onAction
    )

    EditTransactionBottomSheets(
        state = state.value,
        showBottomSheetWithAccount,
        showBottomSheetWithArticles,
        onAction = viewModel::onAction
    )

    EditTransactionDialogs(
        state = state.value,
        showDialogWithAmount,
        showDataPicker,
        showTimePicker,
        showDialog,
        dialogMessage,
        onAction = viewModel::onAction
    )
}
