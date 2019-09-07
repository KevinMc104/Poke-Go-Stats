package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import com.example.pokegostats.model.RapidPokemonGoStats
import com.example.pokegostats.model.RapidPokemonGoTypes
import com.example.pokegostats.room.dao.PokemonAndFormsAndTypesDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonFormEntity
import com.example.pokegostats.room.entity.PokemonTypeEntity
import com.example.pokegostats.service.PokemonGoApiService

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PokemonGoStatsRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonAndFormsAndTypesDao: PokemonAndFormsAndTypesDao,
    var service: PokemonGoApiService
) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAllPokemon()
    val allPokemonFormsAndTypes: LiveData<List<PokemonAndFormsAndTypes>> = pokemonAndFormsAndTypesDao.getAllPokemonAndFormsAndTypes()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insertPokemon() {
        // add pokemon
        val rapidPokemonGoStats: ArrayList<RapidPokemonGoStats> = ArrayList()
        rapidPokemonGoStats.addAll(service.getRapidPokemonGoStats())

        val pokemonListToBeInserted = ArrayList<PokemonEntity>()
        val pokemonFormsListToBeInserted = ArrayList<PokemonFormEntity>()
        for(item in rapidPokemonGoStats) {
            pokemonListToBeInserted.add(PokemonEntity(item.PokemonId, item.BaseAttack, item.BaseDefense, item.BaseStamina, item.PokemonName))
            if(item.Form.isNullOrBlank()) {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(null, item.PokemonId, "Default"))
            } else {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(null, item.PokemonId, item.Form))
            }
        }

        // Batch Insert
        pokemonDao.insertAllPokemon(*pokemonListToBeInserted.toTypedArray())
        val primaryKeys = pokemonDao.insertAllForms(*pokemonFormsListToBeInserted.toTypedArray())

        val rapidPokemonGoTypes: ArrayList<RapidPokemonGoTypes> = ArrayList()
        rapidPokemonGoTypes.addAll(service.getRapidPokemonGoTypes())

        val pokemonTypesListToBeInserted = ArrayList<PokemonTypeEntity>()
        var iterator = 0
        rapidPokemonGoTypes.forEach { pokemon ->
            pokemon.Type.forEach { type ->
                val currentType = PokemonTypeEntity(null, primaryKeys.get(iterator), type)
                pokemonTypesListToBeInserted.add(currentType)
            }
            iterator++
        }
        pokemonDao.insertAllTypes(*pokemonTypesListToBeInserted.toTypedArray())
    }
}