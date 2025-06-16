package com.nikol.yandexschool.features.account.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.domain.state.AccountState
import com.nikol.domain.useCase.GetAccountUseCase
import com.nikol.yandexschool.R
import com.nikol.yandexschool.ui.customUiComponents.AccountTopBar
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon


@Composable
fun AccountScreen(
    viewModel: AccountScreenViewModel = AccountScreenViewModel(GetAccountUseCase())
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AccountTopBar(onClick = {})
        }
    ) {

        when (state.value) {
            is AccountState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AccountState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text((state.value as AccountState.Error).message)
                }
            }

            is AccountState.Success -> {
                val accounts = (state.value as AccountState.Success).items
                Column(modifier = Modifier.fillMaxSize().padding(it)) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(accounts, key = { it.id }) { account ->
                            CustomListItem(
                                modifier = Modifier
                                    .height(57.dp)
                                    .clickable { }
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(horizontal = 16.dp),
                                lead = { EmojiIcon(account.emoji, Color.White) },
                                content = {
                                    Text(account.name, style = MaterialTheme.typography.bodyLarge)
                                },
                                trailing = {
                                    Text(
                                        text = account.balance + " ₽",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowRight,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary.copy(0.3f)
                                    )
                                }
                            )
                        }
                    }
                    CustomListItem(
                        modifier = Modifier
                            .height(56.dp)
                            .clickable { }
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 16.dp),
                        content = {
                            Text(stringResource(R.string.currency), style = MaterialTheme.typography.bodyLarge)
                        },
                        trailing = {
                            Text(
                                text = "₽",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary.copy(0.3f)
                            )
                        }
                    )
                }
            }
        }
    }
}
