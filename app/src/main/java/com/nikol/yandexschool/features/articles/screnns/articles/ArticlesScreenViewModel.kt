package com.nikol.yandexschool.features.articles.screnns.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.state.AmountState
import com.nikol.domain.useCase.GetArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticlesScreenViewModel(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AmountState>(AmountState.Loading)
    val state: StateFlow<AmountState> = _state.asStateFlow()

    init {
        loadAmount()
    }

    private fun loadAmount() {
        _state.value = AmountState.Loading
        viewModelScope.launch {
            try {
                val result = getArticlesUseCase()
                _state.value = AmountState.Success(result)
            } catch (e: Exception) {
                _state.value = AmountState.Error("Не удалось загрузить данные")
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