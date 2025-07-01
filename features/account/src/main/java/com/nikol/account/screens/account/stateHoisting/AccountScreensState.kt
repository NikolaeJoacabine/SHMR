package com.nikol.account.screens.account.stateHoisting

import com.nikol.account.models.AccountUi

/**
 * Состояния экрана аккаунтов, отражающие текущее состояние UI.
 */
sealed class AccountScreensState {
    /** Состояние загрузки данных. */
    object Loading : AccountScreensState()

    /** Состояние ошибки при загрузке данных. */
    object Error : AccountScreensState()

    /** Состояние, когда данных нет (пустой список). */
    object Empty : AccountScreensState()

    /** Состояние отсутствия интернет-соединения. */
    object NoInternet : AccountScreensState()

    /** Состояние с успешно загруженными данными аккаунтов. */
    data class Content(val list: List<AccountUi>) : AccountScreensState()
}
