package com.nikol.ui.formatter

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


fun Long.toDateTimeString(locale: Locale = Locale.getDefault()): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm", locale)
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}