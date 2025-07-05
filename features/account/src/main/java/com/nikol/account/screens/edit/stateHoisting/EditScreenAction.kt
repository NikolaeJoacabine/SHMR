package com.nikol.account.screens.edit.stateHoisting

internal sealed class EditScreenAction {
    data object OnClickNavigateBack : EditScreenAction()
    data class OnClickEditAccount(
        val id: Int,
        val amount: String,
        val name: String
    ) : EditScreenAction()

    data class OnClickDeleteAccount(val id: Int) : EditScreenAction()
    data class OnChangeName(val name: String) : EditScreenAction()
    data class OnChangeAmount(val amount: String) : EditScreenAction()
}
