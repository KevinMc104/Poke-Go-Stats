package com.analytics.pokegostats.room.entity

import androidx.room.*
import java.util.*

/**
 * The purpose of this Entity table is to check how old the current data set
 * is within the other tables. Business Logic for this is in PokemonGoStatsRepository.
 */
@Entity(tableName = "date_cache_table", indices = [Index("date_id")])
@TypeConverters(DateConverter::class)
data class DateCacheEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "date_id") val dateId: Int?,
    @ColumnInfo(name = "date_time") val dateTime: Date?
)

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}