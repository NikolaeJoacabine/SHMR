package com.nikol.yandexschool.features.transaction.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import com.nikol.domain.useCase.GetTransactionsUseCase
import com.nikol.yandexschool.features.transaction.screens.income.state_hoisting.IncomeScreenAction
import com.nikol.yandexschool.features.transaction.screens.income.state_hoisting.IncomeScreenEffect
import com.nikol.yandexschool.features.transaction.screens.income.state_hoisting.IncomeScreenState
import com.nikol.yandexschool.features.transaction.screens.income.state_hoisting.IncomeScreenState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IncomeScreenViewModel(
    private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<IncomeScreenState>(IncomeScreenState.Loading)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<IncomeScreenEffect>()
    val effect = _effect.asSharedFlow()

    init {
        onLoading()
    }

    fun onIntent(action: IncomeScreenAction) {
        when (action) {
            is IncomeScreenAction.OnScreenEntered -> {
                onLoading()
            }

            is IncomeScreenAction.OnFloatingActionButtonClicked -> {
                viewModelScope.launch {
                    _effect.emit(IncomeScreenEffect.NavigateToAddedScreen)
                }
            }

            is IncomeScreenAction.OnHistoryButtonClicked -> {
                viewModelScope.launch {
                    _effect.emit(IncomeScreenEffect.NavigateToHistoryScreen)
                }
            }

            is IncomeScreenAction.OnClickRetryRequest -> {
                onLoading()
            }
        }
    }

    private fun onLoading() {
        _state.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getTransactionsForTodayUseCase(TransactionType.Income).let { result ->
                when (result) {
                    is TransactionState.Success -> {
                        if (result.items.isEmpty()) {
                            _state.value = Empty
                        } else {
                            _state.value = Date(
                                result.items,
                                calculateTotalUseCase(result.items)
                            )
                        }
                    }

                    is TransactionState.Error -> _state.value = Error
                    is TransactionState.NetworkError -> _state.value = NoInternet
                }
            }
        }
    }

    class Factory(
        private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
        private val calculateTotalUseCase: CalculateTotalUseCase
    ) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return IncomeScreenViewModel(getTransactionsForTodayUseCase, calculateTotalUseCase) as T
        }
    }
}

class IncomeScreenViewModelFactoryFactory(
    private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) {
    fun create(): IncomeScreenViewModel.Factory {
        return IncomeScreenViewModel.Factory(getTransactionsForTodayUseCase, calculateTotalUseCase)
    }
}