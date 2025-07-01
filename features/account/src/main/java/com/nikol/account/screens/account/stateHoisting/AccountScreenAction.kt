package com.nikol.account.screens.account.stateHoisting

/**
 * Действия (Actions), которые могут происходить на экране аккаунта.
 * Используются для обработки событий и взаимодействия пользователя с UI.
 */
sealed class AccountScreenAction {
    /** Событие, когда экран аккаунта был открыт (вошли на экран). */
    object OnScreenEntered : AccountScreenAction()

    /** Событие нажатия на кнопку редактирования аккаунта. */
    object OnClickEditButton : AccountScreenAction()

    /** Событие повторной попытки загрузки данных, например после ошибки. */
    object OnRetryClicked : AccountScreenAction()
}
