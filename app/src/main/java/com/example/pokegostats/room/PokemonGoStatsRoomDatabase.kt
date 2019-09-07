package com.example.pokegostats.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokegostats.room.dao.PokemonAndPokemonFormsDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.dao.PokemonFormsAndPokemonTypesDao
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonTypeEntity
import com.example.pokegostats.room.entity.PokemonTypeForm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [PokemonEntity::class, PokemonTypeForm::class, PokemonTypeEntity::class], version = 1)
abstract class PokemonGoStatsRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonAndPokemonFormsDao(): PokemonAndPokemonFormsDao
    abstract fun pokemonFormsAndPokemonTypesDao(): PokemonFormsAndPokemonTypesDao

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
            val pokemon = PokemonEntity(5, 550, 452, 376,  "Squirtle")
            val pokemon2 = PokemonEntity(6, 550, 452, 376,  "Bobby Boi")
            val pokemon3 = PokemonEntity(7, 550, 452, 376,  "Testy Pants")
            pokemonDao.insertAllPokemon(pokemon, pokemon2, pokemon3)

            val pokemonForm = PokemonTypeForm(null, 5,  "Default")
            val pokemonForm2 = PokemonTypeForm(null, 6,  "Flaming Bobby")
            val pokemonForm3 = PokemonTypeForm(null, 6,  "Water Bobby")
            val pokemonForm4 = PokemonTypeForm(null, 7,  "Tight")
            val primaryKeys = pokemonDao.insertAllForms(pokemonForm, pokemonForm2, pokemonForm3, pokemonForm4)

            val type1 = PokemonTypeEntity(null, primaryKeys.get(0), "Water")
            val type2 = PokemonTypeEntity(null, primaryKeys.get(0), "Fire")
            val type3 = PokemonTypeEntity(null, primaryKeys.get(1), "Fire")
            val type4 = PokemonTypeEntity(null, primaryKeys.get(1), "Poison")
            val type5 = PokemonTypeEntity(null, primaryKeys.get(2), "Water")
            val type6 = PokemonTypeEntity(null, primaryKeys.get(3), "Ghost")

            pokemonDao.insertAllTypes(type1, type2, type3, type4, type5, type6)
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