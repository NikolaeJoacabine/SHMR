package com.nikol.yandexschool.ui.nav

data class TopLevelRoute<T : Any>(
    val name: Int,
    val route: T,
    val icon: Int
)
