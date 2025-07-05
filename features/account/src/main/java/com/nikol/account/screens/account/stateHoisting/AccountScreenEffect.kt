package com.nikol.account.screens.account.stateHoisting

/**
 * Эффекты (Side Effects) экрана аккаунта, которые представляют одноразовые события,
 * такие как навигация или показ уведомлений.
 */
sealed class AccountScreenEffect {
    /** Эффект навигации к экрану редактирования счёта*/
    data class NavigateToEditScreen(
        val id: Int,
        val name: String
    ) : AccountScreenEffect()

    /** Эффект открытия нижнего листа*/
    data object OpenModalBottomSheet : AccountScreenEffect()

    /** Эффект скрытия нижнего листа*/
    data object ClosedModalBottomSheet : AccountScreenEffect()
}
