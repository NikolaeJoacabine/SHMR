package com.nikol.data.util

import kotlinx.coroutines.delay
import retrofit2.Response

/**
 * Выполняет повторную попытку выполнения сетевого запроса при ошибке сервера (HTTP 500).
 *
 * @param repeat Количество попыток (по умолчанию 3).
 * @param delayMillis Задержка между попытками в миллисекундах (по умолчанию 2000 мс).
 * @param block Суспенд-функция, которая выполняет сетевой запрос и возвращает [Response].
 *
 * @return [Response] успешного запроса или последний ответ после всех попыток.
 *
 * Повторяет выполнение блока, если получен ответ с кодом 500 или неуспешный ответ.
 * Между попытками делается задержка [delayMillis].
 */
suspend fun <T> retryOnServerError(
    repeat: Int = 3,
    delayMillis: Long = 2000L,
    block: suspend () -> Response<T>
): Response<T> {
    repeat(repeat - 1) {
        val response = block()
        if (response.isSuccessful && response.code() != 500) {
            return response
        }
        delay(delayMillis)
    }
    return block()
}
