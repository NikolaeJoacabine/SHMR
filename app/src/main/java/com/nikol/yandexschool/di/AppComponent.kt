package com.nikol.yandexschool.di

import android.content.Context
import com.nikol.data.account.local.AccountPreferencesDataSource
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun retrofit(): Retrofit
    fun financeAPI(): FinanceAPI
    fun okHttpClient(): OkHttpClient
    fun networkStatusProvider(): NetworkStatusProvider
    fun dataStore(): AccountPreferencesDataSource
    fun context(): Context
}
