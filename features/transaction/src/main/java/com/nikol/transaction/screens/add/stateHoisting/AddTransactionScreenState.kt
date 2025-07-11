package com.nikol.transaction.screens.add.stateHoisting

import com.nikol.domain.model.Account
import com.nikol.domain.model.Articles
import com.nikol.transaction.models.TransactionAddUi

internal sealed class AddTransactionScreenState {

    data class Content(
        val transaction: TransactionAddUi,
        val accounts: AccountsState,
        val articles: ArticlesState
    ) : AddTransactionScreenState()

    data object Loading : AddTransactionScreenState()
}

internal sealed class AccountsState {
    data class Content(
        val list: List<Account>
    ) : AccountsState()

    data object Loading : AccountsState()
    data object Error : AccountsState()
    data object NoInternet : AccountsState()
}

internal sealed class ArticlesState {
    data class Content(
        val articles: List<Articles>
    ) : ArticlesState()
    data object Loading : ArticlesState()
    data object Error : ArticlesState()
    data object NoInternet : ArticlesState()
}
