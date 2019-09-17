package com.example.pokegostats.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.dao.PokemonMovesDao
import com.example.pokegostats.room.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [PokemonEntity::class, PokemonFormEntity::class, PokemonTypeEntity::class, PokemonMovesEntity::class, PokemonWeatherBoostsEntity::class], version = 1)
abstract class PokemonGoStatsRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonMovesDao(): PokemonMovesDao

    private class PokemonGoStatsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.pokemonDao(), database.pokemonMovesDao())
                }
            }
        }

        suspend fun populateDatabase(pokemonDao: PokemonDao, pokemonMovesDao: PokemonMovesDao) {
            // Delete all content here.
            pokemonDao.deleteAll()
            pokemonMovesDao.deleteAll()
            // Test Code
//            val pokemon = PokemonEntity(5, 550, 452, 376,  "Squirtle")
//            val pokemon2 = PokemonEntity(6, 550, 452, 376,  "Bobby Boi")
//            val pokemon3 = PokemonEntity(7, 550, 452, 376,  "Testy Pants")
//            pokemonDao.insertAllPokemon(pokemon, pokemon2, pokemon3)
//
//            val pokemonForm = PokemonFormEntity(null, 5,  "Default")
//            val pokemonForm2 = PokemonFormEntity(null, 6,  "Flaming Bobby")
//            val pokemonForm3 = PokemonFormEntity(null, 6,  "Water Bobby")
//            val pokemonForm4 = PokemonFormEntity(null, 7,  "Tight")
//            val primaryKeys = pokemonDao.insertAllForms(pokemonForm, pokemonForm2, pokemonForm3, pokemonForm4)
//
//            val type1 = PokemonTypeEntity(null, primaryKeys.get(0), "Water")
//            val type2 = PokemonTypeEntity(null, primaryKeys.get(0), "Fire")
//            val type3 = PokemonTypeEntity(null, primaryKeys.get(1), "Fire")
//            val type4 = PokemonTypeEntity(null, primaryKeys.get(1), "Poison")
//            val type5 = PokemonTypeEntity(null, primaryKeys.get(2), "Water")
//            val type6 = PokemonTypeEntity(null, primaryKeys.get(3), "Ghost")
//
//            pokemonDao.insertAllTypes(type1, type2, type3, type4, type5, type6)
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
                context.applicationContext.deleteDatabase("pokemon_database")
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