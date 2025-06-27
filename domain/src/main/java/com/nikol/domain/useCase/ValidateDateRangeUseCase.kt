package com.nikol.domain.useCase

import java.time.LocalDate

/**
 * Use case для проверки валидности диапазона дат.
 *
 * Проверяет, что начальная дата не позже конечной.
 */
class ValidateDateRangeUseCase {

    /**
     * Проверяет, что начальная дата не после конечной.
     *
     * @param start начальная дата диапазона.
     * @param end конечная дата диапазона.
     * @return true, если start не позже end, иначе false.
     */
    fun isValid(start: LocalDate, end: LocalDate): Boolean {
        return !start.isAfter(end)
    }
}
