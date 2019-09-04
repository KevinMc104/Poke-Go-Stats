package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.entity.PokemonEntity

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PokemonGoStatsRespository(private val pokemonDao: PokemonDao) {

    /**
     * TODO: Implement checks for if to call out to the API or to the Room database
     * If Room is empty, call API
     */

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAll()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insert(pokemon: PokemonEntity) {
        pokemonDao.insertAll(pokemon)
    }
}