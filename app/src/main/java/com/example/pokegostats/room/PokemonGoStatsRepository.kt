package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import com.example.pokegostats.room.dao.PokemonAndPokemonFormsDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.dao.PokemonFormsAndPokemonTypesDao
import com.example.pokegostats.room.entity.PokemonAndPokemonForms
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonFormsAndPokemonTypes
import com.example.pokegostats.service.PokemonGoApiService

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PokemonGoStatsRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonAndPokemonFormsDao: PokemonAndPokemonFormsDao,
    private val pokemonFormsAndPokemonTypesDao: PokemonFormsAndPokemonTypesDao,
    var service: PokemonGoApiService
) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAllPokemon()
    val allPokemonForms: LiveData<List<PokemonAndPokemonForms>> = pokemonAndPokemonFormsDao.getAllPokemonAndPokemonForms()
    val allPokemonTypes: LiveData<List<PokemonFormsAndPokemonTypes>> = pokemonFormsAndPokemonTypesDao.getAllPokemonFormsAndPokemonTypes()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insertPokemon() {
        // add pokemon
//        val rapidPokemonGoStats: ArrayList<RapidPokemonGoStats> = ArrayList()
//        rapidPokemonGoStats.addAll(service.getRapidPokemonGoStats())
//
//        val pokemonListToBeInserted = ArrayList<PokemonEntity>()
//        for(item in rapidPokemonGoStats) {
//            val pokemon = PokemonEntity(item.PokemonId, item.BaseAttack, item.BaseDefense, item.BaseStamina, item.PokemonName)
//            pokemonListToBeInserted.add(pokemon)
//        }
//
//        val rapidPokemonGoTypes: ArrayList<RapidPokemonGoTypes> = ArrayList()
//        rapidPokemonGoTypes.addAll(service.getRapidPokemonGoTypes())
//
//        val pokemonTypesListToBeInserted = ArrayList<PokemonTypeEntity>()
//        rapidPokemonGoTypes.forEach { pokemon ->
//            pokemon.Type.forEach { type ->
//                val currentType = PokemonTypeEntity(null, pokemon.PokemonId, type)
//                pokemonTypesListToBeInserted.add(currentType)
//            }
//        }
//
//        // Batch Insert
//        pokemonDao.insertAllPokemon(*pokemonListToBeInserted.toTypedArray())
//        pokemonDao.insertAllTypes(*pokemonTypesListToBeInserted.toTypedArray())
    }
}