package com.nikol.domain.state

import com.nikol.domain.model.Amount

sealed class AmountState {
    object Loading : AmountState()
    data class Success(val items: List<Amount>) : AmountState()
    data class Error(val message: String) : AmountState()
}