package com.nikol.transaction.screens.income.stateHoisting

import com.nikol.transaction.models.TransactionUi

/**
 * Состояние экрана доходов, определяющее, что отображать пользователю.
 */
sealed class IncomeScreenState {

    /** Состояние загрузки данных. Показывается, пока идёт загрузка. */
    data object Loading : IncomeScreenState()

    /**
     * Состояние с успешно загруженными данными.
     *
     * @param list список транзакций доходов
     * @param total сумма всех доходов за период
     */
    data class Date(
        val list: List<TransactionUi>,
        val total: Int
    ) : IncomeScreenState()

    /** Состояние, когда данные успешно загружены, но список пуст. */
    data object Empty : IncomeScreenState()

    /** Состояние ошибки при загрузке данных. */
    data object Error : IncomeScreenState()

    /** Состояние отсутствия подключения к сети. */
    data object NoInternet : IncomeScreenState()
}
