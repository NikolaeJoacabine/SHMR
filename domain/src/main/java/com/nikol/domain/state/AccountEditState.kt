package com.nikol.domain.state

sealed class AccountEditState {
    data object Success: AccountEditState()
    data object Error: AccountEditState()
    data object NoInternet: AccountEditState()
}
