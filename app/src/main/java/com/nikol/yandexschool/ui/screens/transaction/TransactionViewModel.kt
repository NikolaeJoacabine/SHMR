package com.nikol.yandexschool.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikol.yandexschool.domain.model.TransactionType
import com.nikol.yandexschool.domain.state.TransactionState
import com.nikol.yandexschool.domain.useCase.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TransactionState>(TransactionState.Loading)
    val state: StateFlow<TransactionState> = _state.asStateFlow()

    //после вся вот эта обработка перейдет в модуль обработки данных, это временная мера
    fun loadTransactions(type: TransactionType) {
        _state.value = TransactionState.Loading
        viewModelScope.launch {
            try {
                val result = getTransactionsUseCase(type)
                _state.value = TransactionState.Success(result)
            } catch (e: Exception) {
                _state.value = TransactionState.Error("Не удалось загрузить данные")
            }
        }
    }
}