package com.example.pokegostats.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokegostats.room.entity.DateCacheEntity

@Dao
interface DateCacheDao {
    @Query("SELECT count(*) FROM date_cache_table")
    suspend fun checkDateTable(): Int

    // There should only ever be One Entry in this table
    @Query("SELECT * FROM date_cache_table")
    suspend fun getDateData(): List<DateCacheEntity>

    @Insert
    suspend fun insertAllDateCacheEntries(vararg dates: DateCacheEntity)

    @Query("DELETE FROM pokemon_moves_table")
    suspend fun deleteAll()
}