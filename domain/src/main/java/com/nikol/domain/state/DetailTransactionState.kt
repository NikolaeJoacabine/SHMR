package com.nikol.domain.state

import com.nikol.domain.model.TransactionDetail

sealed class DetailTransactionState {
    data class Success(val transaction: TransactionDetail) : DetailTransactionState()
    data object Error : DetailTransactionState()
    data object NoInternet : DetailTransactionState()
}
