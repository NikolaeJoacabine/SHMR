package com.nikol.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikol.data.account.local.database.AccountDao
import com.nikol.data.account.local.database.AccountEntity
import com.nikol.data.account.local.database.deleted.DeletedAccountIdDao
import com.nikol.data.account.local.database.deleted.DeletedAccountIdEntity
import com.nikol.data.articles.local.database.ArticleEntity
import com.nikol.data.articles.local.database.ArticlesDao
import com.nikol.data.transaction.local.database.delete.DeletedTransactionDao
import com.nikol.data.transaction.local.database.delete.DeletedTransactionEntity
import com.nikol.data.transaction.local.database.converter.InstantConverters
import com.nikol.data.transaction.local.database.TransactionDao
import com.nikol.data.transaction.local.database.TransactionEntity

@Database(
    entities = [
        ArticleEntity::class,
        AccountEntity::class,
        TransactionEntity::class,
        DeletedTransactionEntity::class,
        DeletedAccountIdEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(InstantConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun deletedTransactionDao(): DeletedTransactionDao
    abstract fun deletedAccountIdDao(): DeletedAccountIdDao
}
