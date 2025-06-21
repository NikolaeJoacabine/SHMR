package com.nikol.yandexschool.features.articles.screnns.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.state.ArticlesState
import com.nikol.domain.useCase.GetArticlesUseCase
import com.nikol.yandexschool.features.articles.screnns.articles.state_hoisting.ArticlesScreenAction
import com.nikol.yandexschool.features.articles.screnns.articles.state_hoisting.ArticlesScreenEffect
import com.nikol.yandexschool.features.articles.screnns.articles.state_hoisting.ArticlesScreenState
import com.nikol.yandexschool.ui.nav.Articles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticlesScreenViewModel(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ArticlesScreenState>(ArticlesScreenState.Loading)
    val state: StateFlow<ArticlesScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ArticlesScreenEffect>()
    val effect: SharedFlow<ArticlesScreenEffect> = _effect.asSharedFlow()

    init {
        onLoading()
    }

    fun onAction(action: ArticlesScreenAction) {
        when (action) {
            is ArticlesScreenAction.OnScreenEntered -> onLoading()

            is ArticlesScreenAction.OnSearchQueryChanged -> {}
        }
    }

    private fun onLoading() {
        _state.value = ArticlesScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getArticlesUseCase().let { result ->
                when (result) {
                    is ArticlesState.Success -> {
                        if (result.items.isEmpty()) {
                            _state.value = ArticlesScreenState.Empty
                        } else {
                            _state.value = ArticlesScreenState.Content(result.items)
                        }
                    }

                    is ArticlesState.Error, is ArticlesState.Loading -> {
                        ArticlesScreenState.Error
                    }
                }
            }
        }
    }

    class Factory(
        private val getArticlesUseCase: GetArticlesUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticlesScreenViewModel(getArticlesUseCase = getArticlesUseCase) as T
        }
    }
}

class ArticlesScreenViewModelFactoryFactory(
    private val getArticlesUseCase: GetArticlesUseCase
) {

    fun create(): ArticlesScreenViewModel.Factory {
        return ArticlesScreenViewModel.Factory(getArticlesUseCase)
    }
}