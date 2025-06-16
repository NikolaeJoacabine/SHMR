package com.nikol.yandexschool.features.account.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.nikol.domain.state.AccountState
import com.nikol.domain.useCase.GetAccountUseCase
import com.nikol.yandexschool.features.transaction.screens.transaction.TransactionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class AccountScreenViewModel(
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<AccountState>(AccountState.Loading)
    val state: StateFlow<AccountState> = _state.asStateFlow()


    init {
        loadAccount()
    }

    //после вся вот эта обработка перейдет в модуль обработки данных, это временная мера
    private fun loadAccount() {
        _state.value = AccountState.Loading
        viewModelScope.launch {
            try {
                val result = getAccountUseCase()
                _state.value = AccountState.Success(result)
            } catch (e: Exception) {
                _state.value = AccountState.Error("Не удалось загрузить данные")
            }
        }
    }

    class Factory(
        private val getAccountUseCase: GetAccountUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountScreenViewModel(getAccountUseCase) as T
        }
    }
}

class AccountScreenViewModelFactoryFactory(
    private val getAccountUseCase: GetAccountUseCase,
) {
    fun create(): AccountScreenViewModel.Factory {
        return AccountScreenViewModel.Factory(getAccountUseCase)
    }
}