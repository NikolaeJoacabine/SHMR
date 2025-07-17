package com.nikol.transaction.screens.edit.stateHoisting

sealed class EditTransactionScreenEffect {
    data object NavigateBack : EditTransactionScreenEffect()
    data object OpenModalBottomSheetWithAccounts : EditTransactionScreenEffect()
    data object OpenModalBottomSheetWithArticles : EditTransactionScreenEffect()
    data object CloseModalBottomSheetWithAccount : EditTransactionScreenEffect()
    data object CloseModalBottomSheetWithArticles : EditTransactionScreenEffect()
    data class OpenDialogWithAmount(val amount: Int) : EditTransactionScreenEffect()
    data object OpenPickDateDialog : EditTransactionScreenEffect()
    data object OpenPickTimeDialog : EditTransactionScreenEffect()

    data object SuccessfulUpdate : EditTransactionScreenEffect()
    data object SuccessfulDeleteOffline : EditTransactionScreenEffect()
    object SuccessfulDelete : EditTransactionScreenEffect()
    object ShowNotFoundDialog : EditTransactionScreenEffect()
    object SuccessfulUpdateOffline : EditTransactionScreenEffect()
}
