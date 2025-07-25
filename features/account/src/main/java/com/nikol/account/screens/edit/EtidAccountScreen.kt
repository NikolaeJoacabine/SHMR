package com.nikol.account.screens.edit

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.account.R
import com.nikol.account.screens.edit.components.AccountDeleteErrorDialog
import com.nikol.account.screens.edit.components.AccountEditItem
import com.nikol.account.screens.edit.components.DeleteAccountButton
import com.nikol.account.screens.edit.components.EditTopBar
import com.nikol.account.screens.edit.stateHoisting.EditScreenAction
import com.nikol.account.screens.edit.stateHoisting.EditScreenEffect
import com.nikol.account.screens.edit.stateHoisting.EditScreenState

@Composable
internal fun EditAccountScreen(
    viewModel: EditScreenViewModel,
    onNavigateBack: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    var id by remember { mutableStateOf<Int?>(null) }
    var name by remember { mutableStateOf<String?>(null) }
    var balance by remember { mutableStateOf<String?>(null) }

    val showErrorDialog = remember { mutableStateOf(false) }

    val successfulChange = stringResource(R.string.successful_change)
    val successfulDeletion = stringResource(R.string.successful_deletion)

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is EditScreenEffect.NavigateBack -> onNavigateBack()
                is EditScreenEffect.OnEditedAccount -> {
                    Toast.makeText(
                        context,
                        successfulChange,
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateBack()
                }

                is EditScreenEffect.OnShowDialogError -> showErrorDialog.value = true
                is EditScreenEffect.OnDeletedAccount -> {
                    Toast.makeText(
                        context,
                        successfulDeletion,
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateBack()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            EditTopBar(
                name = stringResource(R.string.my_account),
                onClickBack = { viewModel.onAction(EditScreenAction.OnClickNavigateBack) },
                onClickEdit = {
                    //это временно потом переделаю просто не успеваю уже
                    if (id != null && name != null && balance != null) {
                        val amount = balance!!.filter { it.isDigit() }.toDoubleOrNull()
                        if (amount != null) {
                            viewModel.onAction(
                                EditScreenAction.OnClickEditAccount(
                                    id = id!!,
                                    amount = amount.toString(),
                                    name = name!!
                                )
                            )
                        } else {
                            Toast.makeText(context, "Некорректная сумма", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            when (val screenState = state.value) {
                is EditScreenState.Loading,
                is EditScreenState.Content -> {
                    if (screenState is EditScreenState.Content) {
                        id = screenState.account.id
                        name = screenState.account.name
                        balance = screenState.account.balance
                    }
                    AccountEditItem(
                        modifier = Modifier
                            .height(64.dp)
                            .padding(horizontal = 16.dp),
                        accountState = screenState,
                        onNameChange = { newName ->
                            viewModel.onAction(EditScreenAction.OnChangeName(newName))
                        },
                        onBalanceChange = { newBalance ->
                            viewModel.onAction(EditScreenAction.OnChangeAmount(newBalance))
                        }
                    )
                    Spacer(Modifier.size(16.dp))
                    if (screenState is EditScreenState.Content) {
                        DeleteAccountButton {
                            viewModel.onAction(
                                EditScreenAction.OnClickDeleteAccount(
                                    screenState.account.id
                                )
                            )
                        }
                    }
                }

                is EditScreenState.Error -> {
                    Text(
                        text = "Ошибка загрузки данных. Проверьте интернет.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }

                is EditScreenState.Empty -> {
                    Text(
                        text = "Нет данных",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is EditScreenState.OnDeleting,
                is EditScreenState.OnEditing -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    if (showErrorDialog.value) {
        AccountDeleteErrorDialog(
            onDismissRequest = { showErrorDialog.value = false }
        )
    }
}
