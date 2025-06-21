package com.nikol.data.network

import com.nikol.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeader = chain.request().newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.TOKEN}")
            .build()
        return chain.proceed(requestWithHeader)
    }
}