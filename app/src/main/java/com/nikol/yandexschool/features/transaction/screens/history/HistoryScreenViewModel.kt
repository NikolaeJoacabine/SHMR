package com.nikol.yandexschool.features.transaction.screens.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsByPeriodUseCase
import com.nikol.domain.useCase.ValidateDateRangeUseCase
import com.nikol.yandexschool.features.transaction.models.utils.toHistoryUi
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryScreenAction
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryScreenEffect
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryScreenEffect.OpenDatePicker
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryScreenState
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryScreenState.Content
import com.nikol.yandexschool.features.transaction.screens.history.stateHoisting.HistoryTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel для экрана истории транзакций.
 *
 * Отвечает за:
 * - загрузку транзакций за указанный период;
 * - валидацию выбранного диапазона дат;
 * - управление состоянием экрана (загрузка, ошибка, контент и т.п.);
 * - обработку пользовательских действий (выбор даты, переходы и др.).
 *
 * @param transactionType Тип транзакций (расходы/доходы).
 * @param calculateTotalUseCase UseCase для подсчета общей суммы по транзакциям.
 * @param getTransactionsByPeriodUseCase UseCase для получения транзакций за выбранный период.
 * @param validateDateRangeUseCase UseCase для валидации диапазона дат.
 */
class HistoryScreenViewModel(
    private val transactionType: TransactionType,
    private val calculateTotalUseCase: CalculateTotalUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase,
    private val validateDateRangeUseCase: ValidateDateRangeUseCase
) : ViewModel() {


    private var startDate: LocalDate = LocalDate.now().withDayOfMonth(1)
    private var endDate: LocalDate = LocalDate.now()

    private val _state = MutableStateFlow<HistoryScreenState>(
        Content(
            historyTransaction = HistoryTransaction.Loading,
            startDate = startDate,
            endDate = endDate
        )
    )
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HistoryScreenEffect>()
    val effect = _effect.asSharedFlow()

    fun onAction(action: HistoryScreenAction) {
        when (action) {
            is HistoryScreenAction.OnDateSelected -> {
                val newStart = if (action.isStart) action.date else startDate
                val newEnd = if (!action.isStart) action.date else endDate

                if (!validateDateRangeUseCase.isValid(newStart, newEnd)) {
                    emitEffect(HistoryScreenEffect.OpenErrorDateDialog)
                    return
                }

                startDate = newStart
                endDate = newEnd

                updateLoadingState()
                loadTransactions()
            }

            is HistoryScreenAction.OnClickRetryRequest -> loadTransactions()

            is HistoryScreenAction.OnClickedStartDate -> emitEffect(
                OpenDatePicker(startDate, isStart = true)
            )

            is HistoryScreenAction.OnClickedEndDate -> emitEffect(
                OpenDatePicker(endDate, isStart = false)
            )

            is HistoryScreenAction.OnBackButtonClicked -> emitEffect(HistoryScreenEffect.NavigateBack)
            is HistoryScreenAction.OnAnalyticsButtonClick -> emitEffect(HistoryScreenEffect.NavigateAnalyticScreen)
            is HistoryScreenAction.OnTransactionClick -> emitEffect(HistoryScreenEffect.NavigateEditTransactionScreen)
            is HistoryScreenAction.OnScreenEntered -> Unit
        }
    }

    init {
        loadTransactions()
    }

    private fun updateLoadingState() {
        val currentState = _state.value as? Content ?: return
        _state.value = currentState.copy(
            historyTransaction = HistoryTransaction.Loading,
            startDate = startDate,
            endDate = endDate
        )
    }

    private fun loadTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getTransactionsByPeriodUseCase(transactionType, startDate, endDate)

            val currentState = _state.value as? Content ?: return@launch

            val newTransactionState = when (result) {
                is TransactionState.Success -> {
                    val list = result.items
                    if (list.isEmpty()) {
                        HistoryTransaction.Empty
                    } else {
                        val total = calculateTotalUseCase(list)
                        HistoryTransaction.Content(
                            transactions = list.map { it.toHistoryUi() },
                            totalSum = total.toString()
                        )
                    }
                }

                is TransactionState.NetworkError -> HistoryTransaction.NoInternet
                is TransactionState.Error -> HistoryTransaction.Error
            }

            _state.value = currentState.copy(historyTransaction = newTransactionState)
        }
    }


    private fun emitEffect(effect: HistoryScreenEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    override fun onCleared() {
        Log.d("ViewModel", "HistoryScreenViewModel destroy")
    }

    /**
     * Фабрика для создания экземпляров [HistoryScreenViewModel].
     *
     * @param transactionType Тип транзакций (доходы/расходы), который будет использоваться в ViewModel.
     * @param calculateTotalUseCase UseCase для вычисления общей суммы транзакций.
     * @param getTransactionsByPeriodUseCase UseCase для получения транзакций по датам.
     * @param validateDateRangeUseCase UseCase для проверки валидности диапазона дат.
     */
    class Factory(
        private val transactionType: TransactionType,
        private val calculateTotalUseCase: CalculateTotalUseCase,
        private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase,
        private val validateDateRangeUseCase: ValidateDateRangeUseCase

    ) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HistoryScreenViewModel(
                transactionType,
                calculateTotalUseCase,
                getTransactionsByPeriodUseCase,
                validateDateRangeUseCase
            ) as T
        }
    }
}

/**
 * Фабрика-фабрик для создания [HistoryScreenViewModel.Factory] с заранее переданными зависимостями.
 *
 * Используется в DI, чтобы избежать дублирования кода при создании фабрик ViewModel для разных типов транзакций.
 *
 * @param calculateTotalUseCase UseCase для подсчета суммы транзакций.
 * @param getTransactionsByPeriodUseCase UseCase для получения транзакций по периоду.
 * @param validateDateRangeUseCase UseCase для проверки диапазона дат.
 */
class HistoryScreenViewModelFactoryFactory(
    private val calculateTotalUseCase: CalculateTotalUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase,
    private val validateDateRangeUseCase: ValidateDateRangeUseCase
) {

    /**
     * Создает фабрику [HistoryScreenViewModel.Factory] для указанного типа транзакций.
     *
     * @param transactionType Тип транзакции (доход или расход).
     */
    fun create(transactionType: TransactionType): HistoryScreenViewModel.Factory {
        return HistoryScreenViewModel.Factory(
            transactionType,
            calculateTotalUseCase,
            getTransactionsByPeriodUseCase,
            validateDateRangeUseCase
        )
    }
}
