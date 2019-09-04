package com.example.pokegostats.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.entity.PokemonEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(PokemonEntity::class), version = 1)
abstract class PokemonGoStatsRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    private class PokemonGoStatsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.pokemonDao())
                }
            }
        }

        suspend fun populateDatabase(pokemonDao: PokemonDao) {
            // Delete all content here.
            pokemonDao.deleteAll()

            // Add sample words.
            val pokemon = PokemonEntity(1, "500", "Bulbasaur")
            val pokemon2 = PokemonEntity(2, "550", "Squirtle")
            pokemonDao.insertAll(pokemon, pokemon2)

            // TODO: Add your own Pokemon!
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PokemonGoStatsRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PokemonGoStatsRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonGoStatsRoomDatabase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}