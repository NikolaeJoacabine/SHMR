package com.nikol.yandexschool.ui.screens.amount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikol.yandexschool.domain.state.AmountState
import com.nikol.yandexschool.domain.useCase.GetAmountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AmountViewModel(
    private val getAmountUseCase: GetAmountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AmountState>(AmountState.Loading)
    val state: StateFlow<AmountState> = _state.asStateFlow()

    init {
        loadAmount()
    }

    private fun loadAmount() {
        _state.value = AmountState.Loading
        viewModelScope.launch {
            try {
                val result = getAmountUseCase()
                _state.value = AmountState.Success(result)
            } catch (e: Exception) {
                _state.value = AmountState.Error("Не удалось загрузить данные")
            }
        }
    }
}