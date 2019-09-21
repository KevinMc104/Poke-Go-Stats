package com.example.pokegostats.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokegostats.room.dao.DateCacheDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.dao.PokemonMovesDao
import com.example.pokegostats.room.entity.*

@Database(entities = [PokemonEntity::class, PokemonFormEntity::class,
    PokemonTypeEntity::class, PokemonMovesEntity::class,
    PokemonWeatherBoostsEntity::class, DateCacheEntity::class], version = 1)
abstract class PokemonGoStatsRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonMovesDao(): PokemonMovesDao
    abstract fun dateCacheDao(): DateCacheDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PokemonGoStatsRoomDatabase? = null

        fun getDatabase(context: Context): PokemonGoStatsRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonGoStatsRoomDatabase::class.java,

                    "pokemon_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}