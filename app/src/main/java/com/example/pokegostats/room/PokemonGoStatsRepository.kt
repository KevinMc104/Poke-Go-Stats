package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import com.example.pokegostats.model.RapidPokemonGoStats
import com.example.pokegostats.model.RapidPokemonGoTypes
import com.example.pokegostats.room.dao.PokemonAndPokemonTypesDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.entity.PokemonAndPokemonTypes
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonTypeEntity
import com.example.pokegostats.service.PokemonGoApiService

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PokemonGoStatsRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonAndPokemonTypesDao: PokemonAndPokemonTypesDao,
    var service: PokemonGoApiService
) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAllPokemon()
    val allPokemonTypes: LiveData<List<PokemonAndPokemonTypes>> = pokemonAndPokemonTypesDao.getAllPokemonAndPokemonTypes()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insertPokemon() {
        // add pokemon
        val rapidPokemonGoStats: ArrayList<RapidPokemonGoStats> = ArrayList()
        rapidPokemonGoStats.addAll(service.getRapidPokemonGoStats())

        val pokemonListToBeInserted = ArrayList<PokemonEntity>()
        for(item in rapidPokemonGoStats) {
            val pokemon = PokemonEntity(item.PokemonId, item.BaseAttack, item.BaseDefense, item.BaseStamina, item.PokemonName)
            pokemonListToBeInserted.add(pokemon)
        }

        val rapidPokemonGoTypes: ArrayList<RapidPokemonGoTypes> = ArrayList()
        rapidPokemonGoTypes.addAll(service.getRapidPokemonGoTypes())

        val pokemonTypesListToBeInserted = ArrayList<PokemonTypeEntity>()
        rapidPokemonGoTypes.forEach { pokemon ->
            pokemon.Type.forEach { type ->
                val currentType = PokemonTypeEntity(null, pokemon.PokemonId, type)
                pokemonTypesListToBeInserted.add(currentType)
            }
        }

        // Batch Insert
        pokemonDao.insertAllPokemon(*pokemonListToBeInserted.toTypedArray())
        pokemonDao.insertAllTypes(*pokemonTypesListToBeInserted.toTypedArray())
    }
}