package com.nikol.transaction.screens.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.GetCategoryState
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import com.nikol.domain.useCase.GetListWithAnalysisUseCase
import com.nikol.domain.useCase.ValidateDateRangeUseCase
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenAction
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenEffect
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisScreenState
import com.nikol.transaction.screens.analysis.stateHoisting.AnalysisState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class AnalysisScreenViewModel(
    private val transactionType: TransactionType,
    private val getListWithAnalysisUseCase: GetListWithAnalysisUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
    private val validateDateRangeUseCase: ValidateDateRangeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AnalysisScreenState>(
        AnalysisScreenState.Content(
            analysisState = AnalysisState.Loading
        )
    )
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AnalysisScreenEffect>()
    val effect = _effect.asSharedFlow()

    fun onAction(action: AnalysisScreenAction) {
        when (action) {
            is AnalysisScreenAction.OnScreenEntered -> getCurrentData()
            is AnalysisScreenAction.OnNavigateBack -> emitEffect(AnalysisScreenEffect.NavigateBack)
            is AnalysisScreenAction.OnClickEndDate -> emitEffect(AnalysisScreenEffect.OpenEndDateEditDialog)
            is AnalysisScreenAction.OnClickStartDate -> emitEffect(AnalysisScreenEffect.OpenStartDateEditDialog)
            is AnalysisScreenAction.EndDateEdit -> updateEndDate(action.localDate)
            is AnalysisScreenAction.StartDeteEdit -> updateStartDate(action.localDate)
        }
    }

    init {
        onAction(AnalysisScreenAction.OnScreenEntered)
    }

    private fun emitEffect(analysisScreenEffect: AnalysisScreenEffect) {
        viewModelScope.launch {
            _effect.emit(analysisScreenEffect)
        }
    }

    private fun updateStartDate(localDate: LocalDate) {
        val currentState = _state.value as? AnalysisScreenState.Content ?: return
        if (!validateDateRangeUseCase.isValid(localDate, currentState.endDate)) {
            emitEffect(AnalysisScreenEffect.OpenErrorDialog)
            return
        }
        _state.value = currentState.copy(startDate = localDate)
        getCurrentData()
    }

    private fun updateEndDate(localDate: LocalDate) {
        val currentState = _state.value as? AnalysisScreenState.Content ?: return
        if (!validateDateRangeUseCase.isValid(currentState.startDate, localDate)) {
            emitEffect(AnalysisScreenEffect.OpenErrorDialog)
            return
        }
        _state.value = currentState.copy(endDate = localDate)
        getCurrentData()
    }

    private fun getCurrentData() {
        val currentState = _state.value as? AnalysisScreenState.Content ?: return
        updateLoadingState()
        viewModelScope.launch {
            getListWithAnalysisUseCase(
                type = transactionType,
                startDate = currentState.startDate,
                endDate = currentState.endDate
            ).let { result ->
                when (result) {
                    is GetCategoryState.Error -> _state.value =
                        currentState.copy(analysisState = AnalysisState.Error)

                    is GetCategoryState.Success -> {
                        if (result.category.isEmpty()) {
                            _state.value = currentState.copy(analysisState = AnalysisState.Empty)
                            return@launch
                        }
                        val totalSum = result.category.sumOf { it.amount }
                        val currentCurrency = getCurrentCurrencyUseCase()
                        val accountNewState = AnalysisState.Content(
                            list = result.category,
                            totalSum = totalSum,
                            currencyType = currentCurrency
                        )

                        _state.value = currentState.copy(
                            analysisState = accountNewState
                        )
                    }
                }
            }
        }
    }

    private fun updateLoadingState() {
        val currentState = _state.value as? AnalysisScreenState.Content ?: return
        _state.value = currentState.copy(analysisState = AnalysisState.Loading)
    }


    class Factory @AssistedInject constructor(
        @Assisted private val transactionType: TransactionType,
        private val getListWithAnalysisUseCase: GetListWithAnalysisUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
        private val validateDateRangeUseCase: ValidateDateRangeUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AnalysisScreenViewModel(
                transactionType = transactionType,
                getListWithAnalysisUseCase = getListWithAnalysisUseCase,
                getCurrentCurrencyUseCase = getCurrentCurrencyUseCase,
                validateDateRangeUseCase = validateDateRangeUseCase
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(transactionType: TransactionType): AnalysisScreenViewModel.Factory
        }
    }
}
