package com.nikol.account.screens.account.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikol.account.screens.account.stateHoisting.AccountScreensState

@Composable
fun AccountContentState(
    state: AccountScreensState.Content,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.list, key = { it.id }) { account ->
                AccountListItem(account)
            }
        }
        CurrencyListItem()
    }
}
