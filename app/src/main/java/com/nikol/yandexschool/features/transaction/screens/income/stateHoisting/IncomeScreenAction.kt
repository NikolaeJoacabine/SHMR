package com.nikol.yandexschool.features.transaction.screens.income.stateHoisting

/**
 * Действия пользователя на экране доходов (IncomeScreen).
 */
sealed class IncomeScreenAction {

    /** Действие при первом входе на экран. Используется для запуска начальной загрузки. */
    data object OnScreenEntered : IncomeScreenAction()

    /** Действие при нажатии на кнопку добавления (FAB). */
    data object OnFloatingActionButtonClicked : IncomeScreenAction()

    /** Действие при нажатии на кнопку перехода к истории доходов. */
    data object OnHistoryButtonClicked : IncomeScreenAction()

    /** Повторная попытка запроса данных после ошибки. */
    data object OnClickRetryRequest : IncomeScreenAction()

    /**
     * Действие при клике на конкретную статью дохода.
     *
     * @param id идентификатор выбранной статьи
     */
    data class IncomeClicked(val id: Int) : IncomeScreenAction()
}
