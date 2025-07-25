package com.nikol.account.screens.account.stateHoisting

import com.nikol.account.models.AccountUi
import com.nikol.domain.model.Analysis
import com.nikol.domain.model.CurrencyType

/**
 * Состояния экрана аккаунтов, отражающие текущее состояние UI.
 */
internal sealed class AccountScreensState {
    /** Состояние загрузки данных. */
    object Loading : AccountScreensState()

    /** Состояние ошибки при загрузке данных. */
    object Error : AccountScreensState()

    /** Состояние, когда данных нет (пустой список). */
    object Empty : AccountScreensState()

    /** Состояние отсутствия интернет-соединения. */
    object NoInternet : AccountScreensState()

    /** Состояние с успешно загруженными данными аккаунтов. */
    data class Content(
        val list: List<AccountUi>,
        val currency: List<CurrencyType> = emptyList(),
        val currentCurrency: CurrencyType,
        val listAnalysis: List<Analysis> = emptyList()
    ) : AccountScreensState()
}
