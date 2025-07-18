package com.nikol.data.transaction.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nikol.data.account.local.database.AccountEntity
import com.nikol.data.articles.local.database.ArticleEntity
import java.time.Instant

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["account_id"],
            childColumns = ["transaction_account_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ArticleEntity::class,
            parentColumns = ["article_id"],
            childColumns = ["transaction_article_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["transaction_account_id"]),
        Index(value = ["transaction_article_id"])
    ]
)
data class TransactionEntity(
    @PrimaryKey
    @ColumnInfo(name = "transaction_id")
    val id: Int,

    @ColumnInfo(name = "transaction_account_id")
    val accountId: Int,

    @ColumnInfo(name = "transaction_article_id")
    val articleId: Int,

    @ColumnInfo(name = "comment")
    val comment: String?,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "created_at_server")
    val createdAtFromServer: Instant,

    @ColumnInfo(name = "updated_at_server")
    val updatedAtFromServer: Instant,

    @ColumnInfo(name = "is_income")
    val isIncome: Boolean,

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,

    @ColumnInfo(name = "last_synced_at")
    val lastSyncedAt: Long? = null
)
