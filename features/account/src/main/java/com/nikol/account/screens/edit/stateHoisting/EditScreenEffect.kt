package com.nikol.account.screens.edit.stateHoisting

internal sealed class EditScreenEffect {

    data object NavigateBack : EditScreenEffect()
    data object OnEditedAccount : EditScreenEffect()
    data object OnShowDialogError : EditScreenEffect()
    data object OnDeletedAccount : EditScreenEffect()
}
