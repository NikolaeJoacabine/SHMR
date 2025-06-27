package com.nikol.yandexschool.features.articles.screnns.articles

import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.yandexschool.R
import com.nikol.yandexschool.features.articles.screnns.articles.components.ArticlesContentState
import com.nikol.yandexschool.features.articles.screnns.articles.components.ArticlesErrorState
import com.nikol.yandexschool.features.articles.screnns.articles.components.ArticlesLoadingState
import com.nikol.yandexschool.features.articles.screnns.articles.components.NoInternetState
import com.nikol.yandexschool.features.articles.screnns.articles.components.SearchField
import com.nikol.yandexschool.features.articles.screnns.articles.stateHoisting.ArticlesScreenAction
import com.nikol.yandexschool.features.articles.screnns.articles.stateHoisting.ArticlesScreenEffect
import com.nikol.yandexschool.features.articles.screnns.articles.stateHoisting.ArticlesScreenState
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem
import com.nikol.yandexschool.ui.customUiComponents.DefaultTopBar
import com.nikol.yandexschool.ui.customUiComponents.EmojiIcon
import com.nikol.yandexschool.ui.customUiComponents.NoInternet


@Composable
fun ArticlesScreen(
    viewModel: ArticlesScreenViewModel
) {
    val query = rememberSaveable { mutableStateOf("") }
    val state = viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ArticlesScreenEffect.ScrollToTop -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            DefaultTopBar(text = stringResource(R.string.articles))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
                .padding(top = padding.calculateTopPadding())
        ) {
            SearchField(
                value = query.value,
                onValueChange = {
                    query.value = it
                    viewModel.onAction(ArticlesScreenAction.OnSearchQueryChanged(it))
                },
                onSearch = {
                    viewModel.onAction(ArticlesScreenAction.OnSearchQueryChanged(query.value))
                }
            )

            when (val currentState = state.value) {
                is ArticlesScreenState.Loading -> ArticlesLoadingState()
                is ArticlesScreenState.Error -> ArticlesErrorState()
                is ArticlesScreenState.Content -> ArticlesContentState(currentState.list)
                is ArticlesScreenState.Empty -> Unit
                is ArticlesScreenState.NoInternet -> NoInternetState {
                    viewModel.onAction(ArticlesScreenAction.OnClickToRetry)
                }
            }
        }
    }
}
