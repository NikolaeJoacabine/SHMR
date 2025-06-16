package com.nikol.yandexschool.features.articles.screnns.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.domain.state.AmountState
import com.nikol.domain.useCase.GetArticlesUseCase
import com.nikol.yandexschool.R
import com.nikol.yandexschool.ui.customUiComponents.AccountTopBar
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.DefaultTopBar
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon


@Composable
fun ArticlesScreen(
    viewModel: ArticlesScreenViewModel = ArticlesScreenViewModel(GetArticlesUseCase())
) {
    val query = rememberSaveable { mutableStateOf("") }
    val state = viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            DefaultTopBar(stringResource(R.string.articles))
        }
    ) {

        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = query.value,
                onValueChange = { query.value = it },
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                },
                placeholder = { Text(stringResource(R.string.find_an_article)) }
            )

            when (state.value) {
                is AmountState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is AmountState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text((state.value as AmountState.Error).message)
                    }
                }

                is AmountState.Success -> {
                    val filteredList = (state.value as AmountState.Success).items

                    LazyColumn {
                        items(filteredList, key = { it.id }) { amount ->
                            CustomListItem(
                                modifier = Modifier
                                    .height(68.dp)
                                    .padding(horizontal = 16.dp),
                                lead = { EmojiIcon(amount.emoji) },
                                content = { Text(text = amount.name) }
                            )
                        }
                    }
                }
            }
        }
    }
}
