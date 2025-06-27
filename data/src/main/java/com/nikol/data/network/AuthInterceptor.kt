package com.nikol.data.network

import com.nikol.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp Interceptor, который добавляет заголовок Authorization с токеном Bearer к каждому запросу.
 *
 * Используется для автоматического добавления токена авторизации из BuildConfig.TOKEN
 * ко всем исходящим HTTP-запросам.
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeader = chain.request().newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.TOKEN}")
            .build()
        return chain.proceed(requestWithHeader)
    }
}
