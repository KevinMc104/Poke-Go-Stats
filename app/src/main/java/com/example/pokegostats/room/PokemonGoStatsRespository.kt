package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import com.example.pokegostats.model.PokemonGoStats
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.service.PokemonGoService

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
/**
 * TODO: Figure out how to inject service properly
 */
class PokemonGoStatsRespository(private val pokemonDao: PokemonDao, var service: PokemonGoService) {

    /**
     * TODO: Implement checks for if to call out to the API or to the Room database
     * If Room is empty, call API
     */

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAll()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insert() {
        // add pokemon
        val list: ArrayList<PokemonGoStats> = ArrayList()
        list.addAll(service.getPokemonGoStats())

        val pokemonListToBeInserted = ArrayList<PokemonEntity>()
        for(item in list) {
            /**
             * TODO: Add logic to deal with multiple pokemon of same name/id, but diff types
             */
            val pokemon = PokemonEntity(null, item.PokemonId, item.BaseAttack, item.BaseDefense, item.BaseStamina, item.PokemonName)
            pokemonListToBeInserted.add(pokemon)
        }

        // Batch Insert
        pokemonDao.insertAll(*pokemonListToBeInserted.toTypedArray())
    }
}