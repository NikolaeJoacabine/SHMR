package com.nikol.transaction.screens.add.stateHoisting

internal sealed class AddTransactionScreenEffect {
    data object OnBack : AddTransactionScreenEffect()
    data object OnSuccessfulAddedTransaction : AddTransactionScreenEffect()
    data object OpenModalBottomSheetsWithAllAccount : AddTransactionScreenEffect()
    data object CloseModalBottomSheetsWithAllAccount : AddTransactionScreenEffect()
    data object OpenModalBottomSheetsWithAllArticles : AddTransactionScreenEffect()
    data object CloseModalBottomSheetsWithAllArticles : AddTransactionScreenEffect()
    data object OpenAmountDialog : AddTransactionScreenEffect()
    data object CloseAmountDialog : AddTransactionScreenEffect()
    data object OpenDatePickerDialog : AddTransactionScreenEffect()
    data object OpenTimePickerDialog : AddTransactionScreenEffect()
    data object OnShowNotFoundDialog : AddTransactionScreenEffect()
    data object OnOfflineSavedTransaction : AddTransactionScreenEffect()
}
