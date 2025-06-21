package com.nikol.yandexschool.features.transaction.screens.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.CalculateTotalUseCase
import com.nikol.domain.useCase.GetTransactionsByPeriodUseCase
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenAction
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenEffect
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenEffect.OpenDatePicker
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenState
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryScreenState.Content
import com.nikol.yandexschool.features.transaction.screens.history.state_hoisting.HistoryTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class HistoryScreenViewModel(
    private val transactionType: TransactionType,
    private val calculateTotalUseCase: CalculateTotalUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HistoryScreenState>(
        Content(
            historyTransaction = HistoryTransaction.Loading,
            startDate = LocalDate.now().withDayOfMonth(1),
            endDate = LocalDate.now()
        )
    )
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HistoryScreenEffect>()
    val effect = _effect.asSharedFlow()

    fun onAction(action: HistoryScreenAction) {
        when (action) {
            is HistoryScreenAction.OnBackButtonClicked -> {
                viewModelScope.launch {
                    _effect.emit(HistoryScreenEffect.NavigateBack)
                }
            }
            is HistoryScreenAction.OnAnalyticsButtonClick -> {
                viewModelScope.launch {
                    _effect.emit(HistoryScreenEffect.NavigateAnalyticScreen)
                }
            }
            is HistoryScreenAction.OnTransactionClick -> {
                viewModelScope.launch {
                    _effect.emit(HistoryScreenEffect.NavigateEditTransactionScreen)
                }
            }
            is HistoryScreenAction.OnClickedStartDate -> {
                val currentStartDate = (_state.value as? Content)?.startDate
                if (currentStartDate != null) {
                    viewModelScope.launch {
                        _effect.emit(OpenDatePicker(currentStartDate, isStart = true))
                    }
                }
            }
            is HistoryScreenAction.OnClickedEndDate -> {
                val currentEndDate = (_state.value as? Content)?.endDate
                if (currentEndDate != null) {
                    viewModelScope.launch {
                        _effect.emit(OpenDatePicker(currentEndDate, isStart = false))
                    }
                }
            }
            is HistoryScreenAction.OnDateSelected -> {
                val currentState = _state.value as? Content ?: return
                val newStartDate = if (action.isStart) action.date else currentState.startDate
                val newEndDate = if (!action.isStart) action.date else currentState.endDate

                _state.value = currentState.copy(
                    historyTransaction = HistoryTransaction.Loading,
                    startDate = newStartDate,
                    endDate = newEndDate
                )

                loadTransactions(newStartDate, newEndDate)
            }

            is HistoryScreenAction.OnScreenEntered -> {}
            is HistoryScreenAction.OnClickRetryRequest -> {
                val initState = _state.value as Content
                loadTransactions(initState.startDate, initState.endDate)
            }
        }
    }

    init {
        val initState = _state.value as Content
        loadTransactions(initState.startDate, initState.endDate)
    }

    private fun loadTransactions(startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getTransactionsByPeriodUseCase(transactionType, startDate, endDate)
            when (result) {
                is TransactionState.Success -> {
                    val list = result.items
                    val newTransactionState = when {
                        list.isEmpty() -> HistoryTransaction.Empty
                        else -> {
                            val total = calculateTotalUseCase(list)
                            HistoryTransaction.Content(list, total.toString())
                        }
                    }
                    val currentState = _state.value as? Content ?: return@launch
                    _state.value = currentState.copy(historyTransaction = newTransactionState)
                }
                is TransactionState.Error -> {
                    val currentState = _state.value as? Content ?: return@launch
                    _state.value = currentState.copy(historyTransaction = HistoryTransaction.Error)
                }
                is TransactionState.NetworkError -> {
                    val currentState = _state.value as? Content ?: return@launch
                    _state.value = currentState.copy(historyTransaction = HistoryTransaction.NoInternet)
                }
            }
        }
    }

    override fun onCleared() {
        Log.d("ViewModel", "HistoryScreenViewModel destroy")
    }


    class Factory(
        private val transactionType: TransactionType,
        private val calculateTotalUseCase: CalculateTotalUseCase,
        private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
    ) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HistoryScreenViewModel(
                transactionType,
                calculateTotalUseCase,
                getTransactionsByPeriodUseCase
            ) as T
        }
    }
}

class HistoryScreenViewModelFactoryFactory(
    private val calculateTotalUseCase: CalculateTotalUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
) {

    fun create(transactionType: TransactionType): HistoryScreenViewModel.Factory {
        return HistoryScreenViewModel.Factory(
            transactionType,
            calculateTotalUseCase,
            getTransactionsByPeriodUseCase
        )
    }
}