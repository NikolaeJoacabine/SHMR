package com.nikol.data.di

import android.content.Context
import androidx.room.Room
import com.nikol.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        context: Context,
    ): AppDatabase {
        return Room
            .databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "financer"
            ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideArticlesDao(
        appDatabase: AppDatabase
    ) = appDatabase.articlesDao()

    @Provides
    @Singleton
    fun provideAccountDao(
        appDatabase: AppDatabase
    ) = appDatabase.accountDao()

    @Provides
    @Singleton
    fun provideTransactionDao(
        appDatabase: AppDatabase
    ) = appDatabase.transactionDao()

    @Provides
    @Singleton
    fun provideDeletedTransactionDao(
        appDatabase: AppDatabase
    ) = appDatabase.deletedTransactionDao()

    @Provides
    @Singleton
    fun provideDeletedAccountIdDao(
        appDatabase: AppDatabase
    ) = appDatabase.deletedAccountIdDao()
}
