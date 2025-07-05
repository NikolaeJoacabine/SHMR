package com.nikol.di

import android.content.Context
import com.nikol.data.account.local.AccountPreferencesDataSource
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface FeatureDependencies {
    fun retrofit(): Retrofit
    fun financeAPI(): FinanceAPI
    fun okHttpClient(): OkHttpClient
    fun networkStatusProvider(): NetworkStatusProvider
    fun dataStore(): AccountPreferencesDataSource
    fun context(): Context
}
