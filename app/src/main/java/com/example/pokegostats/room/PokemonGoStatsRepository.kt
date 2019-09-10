package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokegostats.model.RapidPokemonGoFastMoves
import com.example.pokegostats.model.RapidPokemonGoMaxCp
import com.example.pokegostats.model.RapidPokemonGoStats
import com.example.pokegostats.model.RapidPokemonGoTypes
import com.example.pokegostats.room.dao.PokemonAndFormsAndTypesDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.dao.PokemonMovesDao
import com.example.pokegostats.room.entity.*
import com.example.pokegostats.service.PokemonGoApiService

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PokemonGoStatsRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonMovesDao: PokemonMovesDao,
    private val pokemonAndFormsAndTypesDao: PokemonAndFormsAndTypesDao,
    var service: PokemonGoApiService
) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAllPokemon()
    val allPokemonMoves: LiveData<List<PokemonMovesEntity>> = pokemonMovesDao.getAllMoves()
    val allPokemonFormsAndTypes: LiveData<List<PokemonAndFormsAndTypes>> = pokemonAndFormsAndTypesDao.getAllPokemonAndFormsAndTypes()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    suspend fun insertPokemon() {
        // TODO: Add logic to not call out to the API every time

        // add pokemon
        val formPrimaryKeys = insertPokemonStats()

        // Update with MaxCp
        updateMaxCp()

        val rapidPokemonGoTypes: ArrayList<RapidPokemonGoTypes> = ArrayList()
        rapidPokemonGoTypes.addAll(service.getRapidPokemonGoTypes())

        val pokemonTypesListToBeInserted = ArrayList<PokemonTypeEntity>()
        var iterator = 0
        rapidPokemonGoTypes.forEach { pokemon ->
            pokemon.Type.forEach { type ->
                val currentType = PokemonTypeEntity(null, formPrimaryKeys.get(iterator), type)
                pokemonTypesListToBeInserted.add(currentType)
            }
            iterator++
        }
        pokemonDao.insertAllTypes(*pokemonTypesListToBeInserted.toTypedArray())
    }

    suspend fun insertPokemonStats(): List<Long> {
        val rapidPokemonGoStats: ArrayList<RapidPokemonGoStats> = ArrayList()
        rapidPokemonGoStats.addAll(service.getRapidPokemonGoStats())

        val pokemonListToBeInserted = ArrayList<PokemonEntity>()
        val pokemonFormsListToBeInserted = ArrayList<PokemonFormEntity>()
        for(item in rapidPokemonGoStats) {
            pokemonListToBeInserted.add(PokemonEntity(item.PokemonId, item.BaseAttack, item.BaseDefense, item.BaseStamina, null, item.PokemonName))
            if(item.Form.isNullOrBlank()) {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(null, item.PokemonId, "Default"))
            } else {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(null, item.PokemonId, item.Form))
            }
        }

        // Batch Insert
        pokemonDao.insertAllPokemon(*pokemonListToBeInserted.toTypedArray())
        return pokemonDao.insertAllForms(*pokemonFormsListToBeInserted.toTypedArray())
    }

    suspend fun getPokemon(pokemonId: Int): PokemonAndFormsAndTypes {
        return pokemonAndFormsAndTypesDao.getPokemon(pokemonId)
    }

    suspend fun updateMaxCp() {
        val rapidPokemonGoMaxCp: ArrayList<RapidPokemonGoMaxCp> = ArrayList()
        rapidPokemonGoMaxCp.addAll(service.getRapidPokemonGoMaxCp())

        for(item in rapidPokemonGoMaxCp) {
            pokemonDao.updateMaxCp(item.MaxCp, item.pokemon_id)
        }
    }

    suspend fun insertMoves() {
        val rapidPokemonGoFastMoves: ArrayList<RapidPokemonGoFastMoves> = ArrayList()
        rapidPokemonGoFastMoves.addAll(service.getRapidPokemonGoFastMoves())

        val pokemonListToBeInserted = ArrayList<PokemonMovesEntity>()
        for(item in rapidPokemonGoFastMoves) {
            pokemonListToBeInserted.add(PokemonMovesEntity(item.Name, item.Duration, item.EnergyDelta, item.Power, item.StaminaLossScaler, item.Type))
        }
        pokemonMovesDao.insertAll(*pokemonListToBeInserted.toTypedArray())
    }
}