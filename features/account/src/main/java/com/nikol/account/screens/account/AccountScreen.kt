package com.nikol.account.screens.account

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.account.screens.account.components.AccountContentState
import com.nikol.account.screens.account.components.AccountErrorState
import com.nikol.account.screens.account.components.AccountLoadingState
import com.nikol.account.screens.account.components.NoInternetState
import com.nikol.account.screens.account.stateHoisting.AccountScreenAction
import com.nikol.account.screens.account.stateHoisting.AccountScreensState
import com.nikol.ui.customUiComponents.AccountTopBar


@Composable
fun AccountScreen(
    viewModel: AccountScreenViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            AccountTopBar(onClick = {})
        }
    ) { padding ->
        when (val currentState = state.value) {
            is AccountScreensState.Loading -> AccountLoadingState(padding)
            is AccountScreensState.Error -> AccountErrorState(padding)
            is AccountScreensState.Content -> AccountContentState(currentState, padding)
            is AccountScreensState.NoInternet -> NoInternetState(
                padding = padding,
                onRetryClick = { viewModel.onAction(AccountScreenAction.OnRetryClicked) }
            )

            is AccountScreensState.Empty -> {

            }
        }
    }
}
