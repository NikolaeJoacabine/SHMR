package com.nikol.domain.state

sealed class AccountDeleteState {

    data object Success : AccountDeleteState()
    data object Conflict : AccountDeleteState()
    data object NoInternet : AccountDeleteState()
    data object Error : AccountDeleteState()
}
