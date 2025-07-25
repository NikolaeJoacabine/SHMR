package com.nikol.domain.model

import java.time.LocalDate

data class Analysis(
    val isNegative: Boolean,
    val amount: Int,
    val date: LocalDate
)