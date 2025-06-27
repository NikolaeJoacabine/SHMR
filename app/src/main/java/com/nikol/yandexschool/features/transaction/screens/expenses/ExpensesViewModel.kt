package com.nikol.yandexschool.features.transaction.screens.expenses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import com.nikol.yandexschool.features.transaction.models.utils.toUi
import com.nikol.yandexschool.features.transaction.screens.expenses.stateHoisting.ExpensesScreenAction
import com.nikol.yandexschool.features.transaction.screens.expenses.stateHoisting.ExpensesScreenEffect
import com.nikol.yandexschool.features.transaction.screens.expenses.stateHoisting.ExpensesScreenEffect.NavigateToAdded
import com.nikol.yandexschool.features.transaction.screens.expenses.stateHoisting.ExpensesScreenEffect.NavigateToDetail
import com.nikol.yandexschool.features.transaction.screens.expenses.stateHoisting.ExpensesScreenEffect.NavigateToHistory
import com.nikol.yandexschool.features.transaction.screens.expenses.stateHoisting.ExpensesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана расходов.
 *
 * Отвечает за:
 * - Загрузку транзакций за текущий день;
 * - Формирование состояния экрана на основе результата;
 * - Обработку пользовательских действий (нажатия на элементы, кнопки и т.д.);
 * - Эмиссию навигационных эффектов.
 *
 * @param getTransactionsForTodayUseCase UseCase для получения транзакций за текущий день.
 * @param calculateTotalUseCase UseCase для подсчета общей суммы расходов.
 */
class ExpensesScreenViewModel(
    private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesScreenState>(ExpensesScreenState.Loading)
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
        _state.value = ExpensesScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getTransactionsForTodayUseCase(TransactionType.Expenses).let { result ->
                when (result) {
                    is TransactionState.Success -> {
                        if (result.items.isEmpty()) {
                            _state.value = ExpensesScreenState.EmptyList
                        } else {
                            _state.value = ExpensesScreenState.Content(
                                result.items.map { it.toUi() },
                                calculateTotalUseCase(result.items)
                            )
                        }
                    }

                    is TransactionState.Error -> {
                        _state.value = ExpensesScreenState.Error
                    }

                    is TransactionState.NetworkError -> {
                        _state.value = ExpensesScreenState.NoInternet
                    }
                }
            }
        }
    }

    /**
     * Фабрика для создания экземпляра [ExpensesScreenViewModel].
     *
     * @param getTransactionsForTodayUseCase UseCase для загрузки сегодняшних транзакций.
     * @param calculateTotalUseCase UseCase для подсчета общей суммы.
     */
    class Factory(
        private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
        private val calculateTotalUseCase: CalculateTotalUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExpensesScreenViewModel(
                getTransactionsForTodayUseCase,
                calculateTotalUseCase
            ) as T
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "onDestroy")
    }
}

/**
 * Вспомогательная фабрика, создающая [ExpensesScreenViewModel.Factory].
 *
 * Используется для внедрения зависимостей в ViewModel через DI-компоненты.
 *
 * @param getTransactionsForTodayUseCase UseCase для получения транзакций за текущий день.
 * @param calculateTotalUseCase UseCase для вычисления общей суммы.
 */
class ExpensesScreenViewModelFactoryFactory(
    private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) {

    /**
     * Создает [ExpensesScreenViewModel.Factory] с необходимыми зависимостями.
     */
    fun create(): ExpensesScreenViewModel.Factory {
        return ExpensesScreenViewModel.Factory(
            getTransactionsForTodayUseCase,
            calculateTotalUseCase
        )
    }
}
