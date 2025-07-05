package com.nikol.transaction.screens.history.stateHoisting

import java.time.LocalDate

/**
 * Действия пользователя и события экрана истории транзакций.
 */
sealed class HistoryScreenAction {

    /** Событие входа на экран истории. */
    object OnScreenEntered : HistoryScreenAction()

    /** Нажатие на транзакцию с указанным идентификатором. */
    data class OnTransactionClick(val id: String) : HistoryScreenAction()

    /** Нажатие на кнопку перехода к аналитике. */
    data object OnAnalyticsButtonClick : HistoryScreenAction()

    /** Нажатие кнопки "назад". */
    data object OnBackButtonClicked : HistoryScreenAction()

    /** Нажатие кнопки повторного запроса данных. */
    data object OnClickRetryRequest : HistoryScreenAction()

    /** Нажатие на выбор начальной даты. */
    data object OnClickedStartDate : HistoryScreenAction()

    /** Нажатие на выбор конечной даты. */
    data object OnClickedEndDate : HistoryScreenAction()

    /**
     * Выбор даты в диалоге.
     * @param date выбранная дата
     * @param isStart true, если выбрана начальная дата, иначе конечная
     */
    data class OnDateSelected(val date: LocalDate, val isStart: Boolean) : HistoryScreenAction()
}
