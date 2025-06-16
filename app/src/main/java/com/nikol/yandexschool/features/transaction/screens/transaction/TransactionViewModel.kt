package com.nikol.yandexschool.features.transaction.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.TransactionState
import com.nikol.domain.useCase.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionViewModel (
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


    class Factory(
        private val getTransactionsUseCase: GetTransactionsUseCase,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TransactionViewModel(getTransactionsUseCase) as T
        }
    }
}

class TransactionScreenViewModelFactoryFactory (
    private val getTransactionsUseCase: GetTransactionsUseCase,
) {

    fun create(): TransactionViewModel.Factory {
        return TransactionViewModel.Factory(getTransactionsUseCase)
    }
}