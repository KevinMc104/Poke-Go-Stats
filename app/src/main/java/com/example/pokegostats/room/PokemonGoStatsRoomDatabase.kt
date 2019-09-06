package com.example.pokegostats.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokegostats.room.dao.PokemonAndPokemonTypesDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonTypeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(PokemonEntity::class, PokemonTypeEntity::class), version = 1)
abstract class PokemonGoStatsRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonAndPokemonTypesDao(): PokemonAndPokemonTypesDao

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
            // Test Code
//            val pokemon = PokemonEntity(5, 550, 452, 376,  "Squirtle")
//            val type1 = Type(null, 5, "Water")
//            val type2 = Type(null, 5, "Fire")
//
//            val pokemon2 = PokemonEntity(6, 550, 452, 376,  "Bobby Boi")
//            val type3 = Type(null, 6, "Fire")
//            val type4 = Type(null, 6, "Poison")
//
//            val pokemon3 = PokemonEntity(7, 550, 452, 376,  "Testy Pants")
//            val type5 = Type(null, 7, "Ghost")
//
//            pokemonDao.insertAllPokemon(pokemon, pokemon2, pokemon3)
//            pokemonDao.insertAllTypes(type1, type2, type3, type4, type5)
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
                )
                    .addCallback(PokemonGoStatsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}