package com.nikol.transaction.screens.edit.stateHoisting

import com.nikol.domain.model.Account
import com.nikol.domain.model.Articles
import com.nikol.transaction.models.TransactionAddUi

sealed class EditTransactionScreenState {
    object Loading : EditTransactionScreenState()
    data class Content(
        val transaction: TransactionAddUi,
        val articlesStateEdit: ArticlesStateEdit = ArticlesStateEdit.Loading,
        val accountsStateEdit: AccountsStateEdit = AccountsStateEdit.Loading
        ) : EditTransactionScreenState()

    data object Error : EditTransactionScreenState()
}

sealed class AccountsStateEdit {
    data object Loading : AccountsStateEdit()
    data class Content(val list: List<Account>) : AccountsStateEdit()
    data object Error : AccountsStateEdit()
}

sealed class ArticlesStateEdit {
    data object Loading : ArticlesStateEdit()
    data class Content(val articles: List<Articles>) : ArticlesStateEdit()
    data object Error : ArticlesStateEdit()
}
