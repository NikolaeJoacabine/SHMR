package com.nikol.transaction.screens.edit.stateHoisting

import com.nikol.domain.model.Account
import com.nikol.domain.model.Articles
import java.time.LocalDate
import java.time.LocalTime

sealed class EditTransactionScreenAction {
    data object NavigateBack : EditTransactionScreenAction()
    data object UpdateTransaction : EditTransactionScreenAction()
    data object SelectAccount : EditTransactionScreenAction()
    data object SelectArticles : EditTransactionScreenAction()
    data class SelectAmount(val amount: Int) : EditTransactionScreenAction()

    data class EditAmount(val amount: Int) : EditTransactionScreenAction()
    data object SelectDate : EditTransactionScreenAction()

    data class EditDate(val date: LocalDate) : EditTransactionScreenAction()
    data object SelectTime : EditTransactionScreenAction()

    data class EditTime(val time: LocalTime) : EditTransactionScreenAction()
    data class UpdateComment(val comment: String) : EditTransactionScreenAction()

    data class EditAccount(val account: Account) : EditTransactionScreenAction()
    data class EditArticles(val articles: Articles) : EditTransactionScreenAction()

    data object DeleteTransaction : EditTransactionScreenAction()
}
