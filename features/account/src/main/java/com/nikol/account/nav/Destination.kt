package com.nikol.account.nav

import kotlinx.serialization.Serializable

@Serializable
internal data object AccountScreen

@Serializable
internal data class EditAccountScreen(
    val id: Int,
    val name: String
)
