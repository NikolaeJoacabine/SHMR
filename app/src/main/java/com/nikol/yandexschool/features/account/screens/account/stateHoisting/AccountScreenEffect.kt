package com.nikol.yandexschool.features.account.screens.account.stateHoisting

/**
 * Эффекты (Side Effects) экрана аккаунта, которые представляют одноразовые события,
 * такие как навигация или показ уведомлений.
 */
sealed class AccountScreenEffect {
    /** Эффект навигации к экрану настроек деталей аккаунта. */
    data object NavigateDetailSettings : AccountScreenEffect()
}
