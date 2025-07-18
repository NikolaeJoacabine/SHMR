package com.nikol.data.transaction.local.database.converter

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverters {

    @TypeConverter
    fun fromInstant(value: Instant?): String? {
        return value?.toString() // ISO-8601
    }

    @TypeConverter
    fun toInstant(value: String?): Instant? {
        return value?.let { Instant.parse(it) }
    }
}
