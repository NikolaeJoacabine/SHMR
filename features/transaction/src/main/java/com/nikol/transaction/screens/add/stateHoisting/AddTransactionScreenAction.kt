package com.nikol.transaction.screens.add.stateHoisting

import java.time.LocalDate
import java.time.LocalTime

internal sealed class AddTransactionScreenAction {
    data object NavigateBack : AddTransactionScreenAction()
    data object AddTransaction : AddTransactionScreenAction()

    /** Действия связаннын с редактировнаием счёта*/
    data object EditAccount : AddTransactionScreenAction()
    data object EditArticles : AddTransactionScreenAction()
    data object EditAmount : AddTransactionScreenAction()
    data object EditDate : AddTransactionScreenAction()
    data object EditTime : AddTransactionScreenAction()
    data class EditComment(val comment: String) : AddTransactionScreenAction()
    data class SelectAccount(val name: String, val id: Int) : AddTransactionScreenAction()
    data class SelectArticles(val name: String, val id: Int) : AddTransactionScreenAction()
    data class SelectAmount(val amount: String) : AddTransactionScreenAction()
    data class SelectDate(val date: LocalDate) : AddTransactionScreenAction()
    data class SelectTime(val time: LocalTime) : AddTransactionScreenAction()

}
