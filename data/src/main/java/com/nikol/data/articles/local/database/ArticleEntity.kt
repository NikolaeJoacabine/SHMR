package com.nikol.data.articles.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "articles",
    indices = [
        Index(value = ["article_name"]),
        Index(value = ["is_income"])
    ]
)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "article_id")
    val id: Int,

    @ColumnInfo(name = "emoji")
    val emoji: String,

    @ColumnInfo(name = "is_income")
    val isIncome: Boolean,

    @ColumnInfo(name = "article_name")
    val name: String,

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,

    @ColumnInfo(name = "last_synced_at")
    val lastSyncedAt: Long? = null
)
