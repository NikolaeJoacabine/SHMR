package com.nikol.di

import android.content.Context
import com.nikol.data.account.local.dataStore.AccountPreferencesDataSource
import com.nikol.data.account.local.database.AccountDao
import com.nikol.data.account.local.database.deleted.DeletedAccountIdDao
import com.nikol.data.articles.local.database.ArticlesDao
import com.nikol.data.database.AppDatabase
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.transaction.local.database.TransactionDao
import com.nikol.data.transaction.local.database.delete.DeletedTransactionDao
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface FeatureDependencies {
    fun retrofit(): Retrofit
    fun financeAPI(): FinanceAPI
    fun okHttpClient(): OkHttpClient
    fun networkStatusProvider(): NetworkStatusProvider
    fun dataStore(): AccountPreferencesDataSource
    fun context(): Context
    fun articlesDao(): ArticlesDao
    fun accountDao(): AccountDao
    fun transactionDao(): TransactionDao
    fun deleteTransactionDao(): DeletedTransactionDao
    fun deleteAccountIdDao(): DeletedAccountIdDao
}
