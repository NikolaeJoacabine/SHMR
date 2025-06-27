package com.nikol.data.util.timeProvider

import com.nikol.domain.common.TimeProvider
import java.time.LocalDateTime

/**
 * Реализация интерфейса [TimeProvider], которая возвращает текущее
 * локальное дату и время.
 *
 * Использует системное время устройства.
 */
class DefaultTimeProvider : TimeProvider {
    override fun now(): LocalDateTime = LocalDateTime.now()
}

