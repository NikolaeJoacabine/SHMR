package com.nikol.account.screens.account.stateHoisting

import com.nikol.domain.model.CurrencyType

/**
 * Действия (Actions), которые могут происходить на экране аккаунта.
 * Используются для обработки событий и взаимодействия пользователя с UI.
 */
sealed class AccountScreenAction {
    /** Событие, когда экран аккаунта был открыт (вошли на экран). */
    data object OnScreenEntered : AccountScreenAction()

    /** Событие нажатия на кнопку редактирования аккаунта. */
    data object OnClickEditButton : AccountScreenAction()

    /** Событие повторной попытки загрузки данных, например после ошибки. */
    data object OnRetryClicked : AccountScreenAction()

    /** Событие нажатия на валюту */
    data object OnClickToCurrentCurrency : AccountScreenAction()

    data object OnClickToCloseModalBottomSheet : AccountScreenAction()

    data class OnClickToCurrency(
        val currencyType: CurrencyType
    ) : AccountScreenAction()
}
