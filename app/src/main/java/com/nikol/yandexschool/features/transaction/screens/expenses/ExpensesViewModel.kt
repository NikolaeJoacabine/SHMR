package com.nikol.yandexschool.features.transaction.screens.expenses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import com.nikol.domain.useCase.GetTransactionsUseCase
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenAction
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenEffect
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenEffect.NavigateToAdded
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenEffect.NavigateToDetail
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenEffect.NavigateToHistory
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenState
import com.nikol.yandexschool.features.transaction.screens.expenses.state_hoisting.ExpensesScreenState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExpensesScreenViewModel(
    private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesScreenState>(Loading)
    val state: StateFlow<ExpensesScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExpensesScreenEffect>()
    val effect: SharedFlow<ExpensesScreenEffect> = _effect

    init {
        Log.d("ViewModel", "ExpensesScreenViewModel created")
        loadTransaction()
    }

    fun onIntent(action: ExpensesScreenAction) {
        when (action) {
            is ExpensesScreenAction.OnScreenEntered -> {
                loadTransaction()
            }

            is ExpensesScreenAction.ExpensesClicked -> {
                viewModelScope.launch {
                    _effect.emit(NavigateToDetail(action.id.toString()))
                }
            }

            is ExpensesScreenAction.FloatingActionButtonClicked -> {
                viewModelScope.launch {
                    _effect.emit(NavigateToAdded)
                }
            }

            ExpensesScreenAction.OnHistoryClicked -> {
                viewModelScope.launch {
                    _effect.emit(NavigateToHistory)
                }
            }

            is ExpensesScreenAction.OnClickRetryRequest -> {
                loadTransaction()
            }
        }
    }


    private fun loadTransaction() {
        _state.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getTransactionsForTodayUseCase(TransactionType.Expenses).let { result ->
                when (result) {
                    is TransactionState.Success -> {
                        if (result.items.isEmpty()) {
                            _state.value = EmptyList
                        } else {
                            _state.value = Content(
                                result.items,
                                calculateTotalUseCase(result.items)
                            )
                        }
                    }

                    is TransactionState.Error -> {
                        _state.value = Error
                    }

                    TransactionState.NetworkError -> {
                        _state.value = NoInternet
                    }
                }
            }
        }
    }

    class Factory(
        private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
        private val calculateTotalUseCase: CalculateTotalUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExpensesScreenViewModel(getTransactionsForTodayUseCase, calculateTotalUseCase) as T
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "onDestroy")
    }
}

class ExpensesScreenViewModelFactoryFactory(
    private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) {

    fun create(): ExpensesScreenViewModel.Factory {
        return ExpensesScreenViewModel.Factory(getTransactionsForTodayUseCase, calculateTotalUseCase)
    }
}
