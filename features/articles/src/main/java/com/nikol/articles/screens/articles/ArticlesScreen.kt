package com.nikol.articles.screens.articles

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.articles.R
import com.nikol.articles.screens.articles.components.ArticlesContentState
import com.nikol.articles.screens.articles.components.ArticlesErrorState
import com.nikol.articles.screens.articles.components.ArticlesLoadingState
import com.nikol.articles.screens.articles.components.NoInternetState
import com.nikol.articles.screens.articles.components.SearchField
import com.nikol.ui.customUiComponents.DefaultTopBar
import com.nikol.articles.screens.articles.stateHoisting.ArticlesScreenAction
import com.nikol.articles.screens.articles.stateHoisting.ArticlesScreenEffect
import com.nikol.articles.screens.articles.stateHoisting.ArticlesScreenState


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
