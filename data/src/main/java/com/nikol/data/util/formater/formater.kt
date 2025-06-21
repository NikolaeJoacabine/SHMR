package com.nikol.data.util.formater

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatCreatedAt(time: String?): String {

    return runCatching {
        val instant = Instant.parse(time)
        val zoned = instant.atZone(ZoneId.systemDefault())
        zoned.format(DateTimeFormatter.ofPattern("HH:mm"))
    }.getOrElse { "" }
}

fun formatAmount(amount: String?): String {
    return amount
        ?.toDoubleOrNull()
        ?.toInt()
        ?.let { NumberFormat.getIntegerInstance(Locale.getDefault()).format(it) }
        ?: "0"
}