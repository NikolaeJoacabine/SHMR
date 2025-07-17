package com.nikol.data.di

import android.content.Context
import com.nikol.data.account.local.dataStore.AccountPreferencesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDateModule {
    @Singleton
    @Provides
    fun provideAccountPreferencesDataSource(context: Context): AccountPreferencesDataSource {
        return AccountPreferencesDataSource(context)
    }
}
