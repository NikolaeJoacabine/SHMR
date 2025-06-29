package com.nikol.yandexschool.features.transaction.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsForTodayUseCase
import com.nikol.yandexschool.features.transaction.models.utils.toUi
import com.nikol.yandexschool.features.transaction.screens.income.stateHoisting.IncomeScreenAction
import com.nikol.yandexschool.features.transaction.screens.income.stateHoisting.IncomeScreenEffect
import com.nikol.yandexschool.features.transaction.screens.income.stateHoisting.IncomeScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана доходов.
 *
 * Отвечает за загрузку данных о доходах за сегодняшний день, вычисление общей суммы
 * и обработку пользовательских интентов, таких как переходы по экрану и повторная загрузка.
 *
 * @param getTransactionsForTodayUseCase UseCase для получения транзакций за сегодня.
 * @param calculateTotalUseCase UseCase для подсчета общей суммы доходов.
 */
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

    /**
     * Обрабатывает пользовательские действия, поступающие с UI.
     *
     * @param action Действие, инициированное пользователем.
     */
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

            is IncomeScreenAction.IncomeClicked -> {
                viewModelScope.launch {
                    _effect.emit(IncomeScreenEffect.NavigateToDetail(action.id.toString()))
                }
            }
        }
    }

    private fun onLoading() {
        _state.value = IncomeScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getTransactionsForTodayUseCase(TransactionType.Income).let { result ->
                when (result) {
                    is TransactionState.Success -> {
                        if (result.items.isEmpty()) {
                            _state.value = IncomeScreenState.Empty
                        } else {
                            _state.value = IncomeScreenState.Date(
                                result.items.map { it.toUi() },
                                calculateTotalUseCase(result.items)
                            )
                        }
                    }

                    is TransactionState.Error -> _state.value = IncomeScreenState.Error
                    is TransactionState.NetworkError -> _state.value = IncomeScreenState.NoInternet
                }
            }
        }
    }

    /**
     * Фабрика для создания экземпляров [IncomeScreenViewModel].
     */
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

/**
 * Фабрика, предоставляющая [IncomeScreenViewModel.Factory] с зависимостями.
 *
 * Используется при сборке графа зависимостей (например, через Dagger).
 */
class IncomeScreenViewModelFactoryFactory(
    private val getTransactionsForTodayUseCase: GetTransactionsForTodayUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) {
    /**
     * Создает [IncomeScreenViewModel.Factory].
     */
    fun create(): IncomeScreenViewModel.Factory {
        return IncomeScreenViewModel.Factory(getTransactionsForTodayUseCase, calculateTotalUseCase)
    }
}
