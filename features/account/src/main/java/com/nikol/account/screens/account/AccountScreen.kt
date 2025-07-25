package com.nikol.account.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.account.R
import com.nikol.account.screens.account.components.AccountContentState
import com.nikol.account.screens.account.components.AccountErrorState
import com.nikol.account.screens.account.components.AccountLoadingState
import com.nikol.account.screens.account.components.NoInternetState
import com.nikol.account.screens.account.stateHoisting.AccountScreenAction
import com.nikol.account.screens.account.stateHoisting.AccountScreenEffect
import com.nikol.account.screens.account.stateHoisting.AccountScreensState
import com.nikol.domain.model.CurrencyType
import com.nikol.ui.customUiComponents.AccountTopBar
import com.nikol.ui.customUiComponents.CustomListItem

@Composable
internal fun AccountScreen(
    viewModel: AccountScreenViewModel,
    navigateToEditScreen: (id: Int, name: String) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val isOpenModal = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onAction(AccountScreenAction.OnScreenEntered)
    }

    HandleAccountEffects(viewModel, navigateToEditScreen, isOpenModal)

    Scaffold(
        topBar = {
            AccountTopBar {
                viewModel.onAction(AccountScreenAction.OnClickEditButton)
            }
        }
    ) { padding ->
        RenderAccountScreenState(state.value, padding) {
            viewModel.onAction(AccountScreenAction.OnClickToCurrentCurrency)
        }
    }

    if (isOpenModal.value) {
        CurrencySelectionModal(state.value, onDismiss = {
            viewModel.onAction(AccountScreenAction.OnClickToCloseModalBottomSheet)
        }) { selectedCurrency ->
            viewModel.onAction(AccountScreenAction.OnClickToCurrency(selectedCurrency))
        }
    }
}

@Composable
private fun HandleAccountEffects(
    viewModel: AccountScreenViewModel,
    navigateToEditScreen: (id: Int, name: String) -> Unit,
    isOpenModal: MutableState<Boolean>
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AccountScreenEffect.NavigateToEditScreen -> navigateToEditScreen(
                    effect.id,
                    effect.name
                )

                is AccountScreenEffect.OpenModalBottomSheet -> isOpenModal.value = true
                is AccountScreenEffect.ClosedModalBottomSheet -> isOpenModal.value = false
            }
        }
    }
}

@Composable
private fun RenderAccountScreenState(
    state: AccountScreensState,
    padding: PaddingValues,
    onCurrencyClick: () -> Unit
) {
    when (state) {
        is AccountScreensState.Loading -> AccountLoadingState(padding)
        is AccountScreensState.Error -> AccountErrorState(padding)
        is AccountScreensState.Content -> AccountContentState(state, padding, onCurrencyClick)
        is AccountScreensState.NoInternet -> NoInternetState(padding) {
            onCurrencyClick()
        }

        is AccountScreensState.Empty -> {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencySelectionModal(
    state: AccountScreensState,
    onDismiss: () -> Unit,
    onCurrencySelected: (CurrencyType) -> Unit
) {
    if (state !is AccountScreensState.Content) return

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.fillMaxWidth()) {
            state.currency.forEach { currencyType ->
                CurrencyItem(currencyType) { onCurrencySelected(currencyType) }
            }
            CancelItem(onClick = onDismiss)
        }
    }
}

@Composable
private fun CurrencyItem(
    currencyType: CurrencyType,
    onClick: () -> Unit
) {
    CustomListItem(
        modifier = Modifier
            .clickable(onClick = onClick)
            .height(71.dp)
            .padding(horizontal = 16.dp),
        lead = {
            Icon(
                painter = painterResource(
                    when (currencyType) {
                        CurrencyType.RUB -> R.drawable.rub
                        CurrencyType.USD -> R.drawable.usd
                        CurrencyType.EUR -> R.drawable.eur
                    }
                ),
                contentDescription = null
            )
        },
        content = {
            Text(
                text = currencyType.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}

@Composable
private fun CancelItem(onClick: () -> Unit) {
    CustomListItem(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.error)
            .clickable(onClick = onClick)
            .height(71.dp)
            .padding(horizontal = 16.dp),
        lead = {
            Icon(
                painter = painterResource(R.drawable.cancel),
                contentDescription = null,
                tint = Color.White
            )
        },
        content = {
            Text(
                text = stringResource(R.string.cancel),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    )
}
