package com.nikol.yandexschool.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikol.yandexschool.domain.state.AccountState
import com.nikol.yandexschool.domain.useCase.GetAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
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
}