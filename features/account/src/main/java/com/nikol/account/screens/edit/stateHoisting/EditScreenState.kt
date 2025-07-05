package com.nikol.account.screens.edit.stateHoisting

import com.nikol.account.models.AccountUi
import com.nikol.domain.model.CurrencyType

/**
 * Состояния экрана редактирования, отражающие текущее состояние UI.
 */
internal sealed class EditScreenState {
    data object Error : EditScreenState()
    data class Content(val account: AccountUi, val currencyType: CurrencyType) : EditScreenState()
    data object Empty : EditScreenState()
    data object Loading : EditScreenState()
    data object OnEditing : EditScreenState()
    data object OnDeleting : EditScreenState()
}
