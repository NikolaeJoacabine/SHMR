package com.nikol.ui.utils

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatAmountToUi(amount: Int): String {
    return amount
        .let { NumberFormat.getIntegerInstance(Locale.getDefault()).format(it) }
        ?: "0"
}

fun formatAmount(raw: String): String {
    if (raw.isEmpty()) return ""
    return raw.reversed()
        .chunked(3)
        .joinToString(" ") { it.reversed() }
        .reversed()
}

fun formatCreatedAt(instant: Instant): String {
    return instant
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun formatCreatedAtHistory(instant: Instant): String {
    return instant
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
}

fun getCurrency(currency: String): String {
    return when (currency) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> "₽"
    }
}
