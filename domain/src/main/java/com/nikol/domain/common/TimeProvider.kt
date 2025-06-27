package com.nikol.domain.common

import java.time.LocalDateTime

/**
 * Интерфейс для получения текущего времени.
 *
 * Позволяет абстрагироваться от источника времени,
 * чтобы упростить тестирование и замену реализации.
 */
interface TimeProvider {
    /**
     * Возвращает текущую дату и время.
     */
    fun now(): LocalDateTime
}
