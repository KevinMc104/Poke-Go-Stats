package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import com.example.pokegostats.model.*
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

    suspend fun getPokemon(pokemonId: Int, pokemonFormName: String): PokemonAndFormsAndTypes {
        return pokemonAndFormsAndTypesDao.getPokemon(pokemonId, pokemonFormName)
    }

    suspend fun getMove(moveName: String): PokemonMovesEntity {
        return pokemonMovesDao.getMove(moveName)
    }

    suspend fun insertPokemon() {
        // TODO: Add logic to not call out to the API every time app is opened

        // add pokemon
        insertPokemonStats()

        // Update with MaxCp
        updateMaxCp()
    }

    private suspend fun insertPokemonStats() {
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
        insertPokemonTypesAndWeatherBoosts(pokemonDao.insertAllForms(*pokemonFormsListToBeInserted.toTypedArray()))
    }

    private suspend fun insertPokemonTypesAndWeatherBoosts(formTablePrimaryKeys: List<Long>) {
        // Get Types list
        val rapidPokemonGoTypes: ArrayList<RapidPokemonGoTypes> = ArrayList()
        rapidPokemonGoTypes.addAll(service.getRapidPokemonGoTypes())

        // Get Weather Boosts
        val weatherBoost = service.getRapidPokemonGoWeatherBoosts()

        val typeList = ArrayList<PokemonTypeEntity>()
        var weatherBoostList = ArrayList<PokemonWeatherBoostsEntity>()
        var formTableIterator = 0
        var typePrimaryKey = 1
        rapidPokemonGoTypes.forEach { pokemon ->
            pokemon.Type.forEach { type ->
                typeList.add(PokemonTypeEntity(typePrimaryKey, formTablePrimaryKeys.get(formTableIterator), type))
                // Check if Type is matched to any number of weather boosts
                weatherBoostList = checkAllWeatherTypesByType(weatherBoost, weatherBoostList, type, typePrimaryKey)
                typePrimaryKey++
            }
            formTableIterator++
        }
        pokemonDao.insertAllTypes(*typeList.toTypedArray())
        pokemonDao.insertAllWeatherBoosts(*weatherBoostList.toTypedArray())
    }

    private suspend fun checkAllWeatherTypesByType(weatherBoost: RapidPokemonGoWeatherBoosts,
                                                   weatherBoostList: ArrayList<PokemonWeatherBoostsEntity>,
                                                   type: String,
                                                   currKey: Int): ArrayList<PokemonWeatherBoostsEntity> {
        weatherBoost.Clear.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Clear"))
            }
        }
        weatherBoost.Cloudy.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Cloudy"))
            }
        }
        weatherBoost.Fog.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Fog"))
            }
        }
        weatherBoost.PartlyCloudy.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Partly Cloudy"))
            }
        }
        weatherBoost.Rain.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Rain"))
            }
        }
        weatherBoost.Snow.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Snow"))
            }
        }
        weatherBoost.Sunny.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Sunny"))
            }
        }
        weatherBoost.Wind.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currKey.toLong(), "Wind"))
            }
        }
        return weatherBoostList
    }

    private suspend fun updateMaxCp() {
        val rapidPokemonGoMaxCp: ArrayList<RapidPokemonGoMaxCp> = ArrayList()
        rapidPokemonGoMaxCp.addAll(service.getRapidPokemonGoMaxCp())

        for(item in rapidPokemonGoMaxCp) {
            pokemonDao.updateMaxCp(item.MaxCp, item.pokemon_id)
        }
    }

    suspend fun insertMoves() {
        var list = getFastMoves()
        list = getChargedMoves(list)

        pokemonMovesDao.insertAll(*list.toTypedArray())
    }

    private suspend fun getFastMoves(): ArrayList<PokemonMovesEntity> {
        val moves: ArrayList<RapidPokemonGoFastMoves> = ArrayList()
        moves.addAll(service.getRapidPokemonGoFastMoves())

        val list = ArrayList<PokemonMovesEntity>()
        for(item in moves) {
            list.add(PokemonMovesEntity(item.Name, item.Duration, null, item.EnergyDelta, item.Power, item.StaminaLossScaler, item.Type))
        }
        return list
    }

    private suspend fun getChargedMoves(list: ArrayList<PokemonMovesEntity>): ArrayList<PokemonMovesEntity> {
        val moves: ArrayList<RapidPokemonGoChargedMoves> = ArrayList()
        moves.addAll(service.getRapidPokemonGoChargedMoves())

        for(item in moves) {
            list.add(PokemonMovesEntity(item.Name, item.Duration, item.CriticalChance, item.EnergyDelta, item.Power, item.StaminaLossScaler, item.Type))
        }
        return list
    }
}