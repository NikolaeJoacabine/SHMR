package com.nikol.data.util.formater

import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Форматирует строковое представление суммы в читаемый числовой формат с разделением тысяч.
 *
 * Если `amount` можно преобразовать в число, возвращает отформатированную строку,
 * иначе возвращает "0".
 *
 * Пример: "12345.67" -> "12,345" (в зависимости от локали).
 */
fun formatAmount(amount: String?): String {
    return amount
        ?.toDoubleOrNull()
        ?.toInt()
        ?.let { NumberFormat.getIntegerInstance(Locale.getDefault()).format(it) }
        ?: "0"
}

/**
 * Преобразует строковое представление суммы в целочисленное значение.
 *
 * Если `amount` невалидна, возвращает 0.
 */
fun formatAmountToInt(amount: String?): Int {
    return amount
        ?.toDoubleOrNull()
        ?.toInt()
        ?: 0
}

/**
 * Парсит строку времени в объект [Instant].
 *
 * Если парсинг не удался, возвращает [Instant.EPOCH] (1970-01-01T00:00:00Z).
 */
fun parseCreatedAt(time: String?): Instant {
    return runCatching { Instant.parse(time) }.getOrElse { Instant.EPOCH }
}

/**
 * Форматирует объект [LocalDate] в строку формата "yyyy-MM-dd",
 * подходящего для API.
 */
fun LocalDate.toApiFormat(): String =
    format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
