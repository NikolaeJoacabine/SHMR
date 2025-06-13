package com.nikol.yandexschool.ui.screens.account

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nikol.yandexschool.domain.state.AccountState
import com.nikol.yandexschool.domain.useCase.GetAccountUseCase
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = AccountViewModel(GetAccountUseCase())
) {
    val state = viewModel.state.collectAsState()

    when (state.value) {
        is AccountState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AccountState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text((state.value as AccountState.Error).message)
            }
        }

        is AccountState.Success -> {
            val accounts = (state.value as AccountState.Success).items
            Column(modifier = Modifier.fillMaxSize()) {
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
                        Text("Валюта", style = MaterialTheme.typography.bodyLarge)
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
