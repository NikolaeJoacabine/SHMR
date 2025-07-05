package com.nikol.transaction.screens.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.CurrencyType
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import com.nikol.domain.useCase.GetTransactionsByPeriodUseCase
import com.nikol.domain.useCase.ValidateDateRangeUseCase
import com.nikol.transaction.models.utils.toHistoryUi
import com.nikol.transaction.screens.history.stateHoisting.HistoryScreenAction
import com.nikol.transaction.screens.history.stateHoisting.HistoryScreenEffect
import com.nikol.transaction.screens.history.stateHoisting.HistoryScreenState
import com.nikol.transaction.screens.history.stateHoisting.HistoryTransaction
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

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
    private val validateDateRangeUseCase: ValidateDateRangeUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase
) : ViewModel() {


    private var startDate: LocalDate = LocalDate.now().withDayOfMonth(1)
    private var endDate: LocalDate = LocalDate.now()

    private val _state = MutableStateFlow<HistoryScreenState>(
        HistoryScreenState.Content(
            historyTransaction = HistoryTransaction.Loading,
            startDate = startDate,
            endDate = endDate,
            currencyType = CurrencyType.RUB
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
                HistoryScreenEffect.OpenDatePicker(startDate, isStart = true)
            )

            is HistoryScreenAction.OnClickedEndDate -> emitEffect(
                HistoryScreenEffect.OpenDatePicker(endDate, isStart = false)
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
        val currentState = _state.value as? HistoryScreenState.Content ?: return
        _state.value = currentState.copy(
            historyTransaction = HistoryTransaction.Loading,
            startDate = startDate,
            endDate = endDate
        )
    }

    private fun loadTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getTransactionsByPeriodUseCase(transactionType, startDate, endDate)
            val currency = getCurrency()
            val currentState = _state.value as? HistoryScreenState.Content ?: return@launch

            val newTransactionState = when (result) {
                is TransactionState.Success -> {
                    val list = result.items
                    if (list.isEmpty()) {
                        HistoryTransaction.Empty
                    } else {
                        val total = calculateTotalUseCase(list)
                        HistoryTransaction.Content(
                            transactions = list.map { it.toHistoryUi() },
                            totalSum = total.toString(),
                        )
                    }
                }

                is TransactionState.NetworkError -> HistoryTransaction.NoInternet
                is TransactionState.Error -> HistoryTransaction.Error
            }

            _state.value = currentState.copy(
                historyTransaction = newTransactionState,
                currencyType = currency
            )
        }
    }


    private fun emitEffect(effect: HistoryScreenEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private suspend fun getCurrency(): CurrencyType {
        return getCurrentCurrencyUseCase()
    }

    /**
     * Фабрика для создания экземпляров [HistoryScreenViewModel].
     *
     * @param transactionType Тип транзакций (доходы/расходы), который будет использоваться в ViewModel.
     * @param calculateTotalUseCase UseCase для вычисления общей суммы транзакций.
     * @param getTransactionsByPeriodUseCase UseCase для получения транзакций по датам.
     * @param validateDateRangeUseCase UseCase для проверки валидности диапазона дат.
     */
    class Factory @AssistedInject constructor(
        @Assisted private val transactionType: TransactionType,
        private val calculateTotalUseCase: CalculateTotalUseCase,
        private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase,
        private val validateDateRangeUseCase: ValidateDateRangeUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase

    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HistoryScreenViewModel(
                transactionType = transactionType,
                calculateTotalUseCase = calculateTotalUseCase,
                getTransactionsByPeriodUseCase = getTransactionsByPeriodUseCase,
                validateDateRangeUseCase = validateDateRangeUseCase,
                getCurrentCurrencyUseCase = getCurrentCurrencyUseCase
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted transactionType: TransactionType
            ): HistoryScreenViewModel.Factory
        }
    }
}
