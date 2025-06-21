package com.nikol.data.util

import kotlinx.coroutines.delay
import retrofit2.Response


suspend fun <T> retryOnServerError(
    times: Int = 3,
    delayMillis: Long = 3000L,
    block: suspend () -> Response<T>
): Response<T> {
    repeat(times - 1) {
        val response = block()
        if (response.isSuccessful && response.code() != 500) {
            return response
        }
        delay(delayMillis)
    }
    return block()
}