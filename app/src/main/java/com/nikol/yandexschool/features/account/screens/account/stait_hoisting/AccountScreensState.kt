package com.nikol.yandexschool.features.account.screens.account.stait_hoisting

import com.nikol.domain.model.Account

sealed class AccountScreensState {
    object Loading : AccountScreensState()
    object Error : AccountScreensState()
    data class Content(val list: List<Account>) : AccountScreensState()
    object Empty : AccountScreensState()
}