package com.nikol.yandexschool.features.articles.screnns.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.Articles
import com.nikol.domain.state.ArticlesState
import com.nikol.domain.useCase.GetArticlesUseCase
import com.nikol.domain.useCase.SearchArticlesUseCase
import com.nikol.yandexschool.features.articles.screnns.articles.stateHoisting.ArticlesScreenAction
import com.nikol.yandexschool.features.articles.screnns.articles.stateHoisting.ArticlesScreenEffect
import com.nikol.yandexschool.features.articles.screnns.articles.stateHoisting.ArticlesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана статей (категорий).
 *
 * Отвечает за:
 * - Загрузку списка статей с сервера;
 * - Обновление UI-состояния в зависимости от результата;
 * - Поиск статей по пользовательскому запросу;
 * - Обработку пользовательских действий и навигационных событий.
 *
 * @param getArticlesUseCase UseCase для получения списка статей.
 * @param searchArticlesUseCase UseCase для фильтрации статей по поисковому запросу.
 */
class ArticlesScreenViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ArticlesScreenState>(ArticlesScreenState.Loading)
    val state: StateFlow<ArticlesScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ArticlesScreenEffect>()
    val effect: SharedFlow<ArticlesScreenEffect> = _effect.asSharedFlow()

    private var originalList: List<Articles> = emptyList()


    init {
        onLoading()
    }

    fun onAction(action: ArticlesScreenAction) {
        when (action) {
            is ArticlesScreenAction.OnScreenEntered -> onLoading()

            is ArticlesScreenAction.OnSearchQueryChanged -> {
                onSearch(action.query)
            }

            is ArticlesScreenAction.OnClickToRetry -> onLoading()

        }
    }

    private fun onLoading() {
        _state.value = ArticlesScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getArticlesUseCase().let { result ->
                when (result) {
                    is ArticlesState.Success -> {
                        originalList = result.items
                        _state.value = if (result.items.isEmpty()) {
                            ArticlesScreenState.Empty
                        } else {
                            ArticlesScreenState.Content(result.items)
                        }
                    }


                    is ArticlesState.Error -> {
                        _state.value = ArticlesScreenState.Error
                    }

                    is ArticlesState.NetworkError -> {
                        _state.value = ArticlesScreenState.NoInternet
                    }
                }
            }
        }
    }

    private fun onSearch(query: String) {
        val filtered = searchArticlesUseCase.searchArticles(originalList, query)
        _state.value = if (filtered.isEmpty()) {
            ArticlesScreenState.Empty
        } else {
            ArticlesScreenState.Content(filtered)
        }
    }

    /**
     * Фабрика для создания экземпляра [ArticlesScreenViewModel].
     *
     * @param getArticlesUseCase UseCase для загрузки статей.
     * @param searchArticlesUseCase UseCase для фильтрации статей по запросу.
     */
    class Factory(
        private val getArticlesUseCase: GetArticlesUseCase,
        private val searchArticlesUseCase: SearchArticlesUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticlesScreenViewModel(getArticlesUseCase, searchArticlesUseCase) as T
        }
    }
}

/**
 * Фабрика-фабрик для создания [ArticlesScreenViewModel.Factory].
 *
 * Используется в DI или других механизмах внедрения зависимостей
 * для передачи UseCase в ViewModel.
 *
 * @param getArticlesUseCase UseCase для получения списка статей.
 * @param searchArticlesUseCase UseCase для поиска по статьям.
 */
class ArticlesScreenViewModelFactoryFactory(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase
) {

    /**
     * Создает экземпляр [ArticlesScreenViewModel.Factory].
     */
    fun create(): ArticlesScreenViewModel.Factory {
        return ArticlesScreenViewModel.Factory(getArticlesUseCase, searchArticlesUseCase)
    }
}
