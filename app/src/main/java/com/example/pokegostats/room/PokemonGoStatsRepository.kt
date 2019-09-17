package com.example.pokegostats.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.pokegostats.model.*
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.dao.PokemonMovesDao
import com.example.pokegostats.room.entity.*
import com.example.pokegostats.service.PokemonGoApiService

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PokemonGoStatsRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonMovesDao: PokemonMovesDao,
    var service: PokemonGoApiService
) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPokemonMoves: LiveData<List<PokemonMovesEntity>> = pokemonMovesDao.getAllMoves()
    val allPokemonFormsTypesWeatherBoosts: LiveData<List<PokemonFormsTypesWeatherBoosts>> = Transformations.map(pokemonDao.getAllPokemonFormsTypesWeatherBoosts(), ::flattenAllPokemonLists)

    suspend fun getPokemon(pokemonId: Int, pokemonFormId: Int): PokemonFormsTypesWeatherBoosts {
        return flattenPokemonLists(pokemonDao.getPokemon(pokemonId, pokemonFormId))
    }

    suspend fun getMove(moveName: String): PokemonMovesEntity {
        return pokemonMovesDao.getMove(moveName)
    }

    suspend fun insertPokemon() {
        // TODO: Add logic to not call out to the API every time app is opened

        // Add Pokemon
        insertPokemonStats()

        // Add Types and Weather Boosts
        insertPokemonTypesAndWeatherBoosts()
    }

    private suspend fun insertPokemonStats() {
        val rapidPokemonGoStats: ArrayList<RapidPokemonGoStats> = ArrayList()
        rapidPokemonGoStats.addAll(service.getRapidPokemonGoStats())

        val pokemonListToBeInserted = ArrayList<PokemonEntity>()
        val pokemonFormsListToBeInserted = ArrayList<PokemonFormEntity>()
        var formsPrimaryKey = 1L
        for(item in rapidPokemonGoStats) {
            pokemonListToBeInserted.add(PokemonEntity(item.PokemonId, item.BaseAttack, item.BaseDefense, item.BaseStamina, null, item.PokemonName, null, null))
            if(item.Form.isNullOrBlank()) {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(formsPrimaryKey, item.PokemonId, "Default"))
            } else {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(formsPrimaryKey, item.PokemonId, item.Form))
            }
            formsPrimaryKey++
        }

        // Batch Insert
        pokemonDao.insertAllPokemon(*pokemonListToBeInserted.toTypedArray())

        // Insert Forms based off Pokemon ID
        pokemonDao.insertAllPokemonForms(*pokemonFormsListToBeInserted.toTypedArray())
    }

    private suspend fun insertPokemonTypesAndWeatherBoosts() {
        // Get Types list
        val rapidPokemonGoTypes: ArrayList<RapidPokemonGoTypes> = ArrayList()
        rapidPokemonGoTypes.addAll(service.getRapidPokemonGoTypes())

        // Get Weather Boosts
        val weatherBoost = service.getRapidPokemonGoWeatherBoosts()

        var weatherBoostList = ArrayList<PokemonWeatherBoostsEntity>()
        var typePrimaryKey = 1L
        rapidPokemonGoTypes.forEach { pokemon ->
            pokemon.Type.forEach { type ->
                if(pokemon.Form.isNullOrBlank()) {
                    pokemonDao.insertType(typePrimaryKey, pokemon.PokemonId, "Default", type)
                } else {
                    pokemonDao.insertType(typePrimaryKey, pokemon.PokemonId, pokemon.Form, type)
                }
                // Check if Type is matched to any number of weather boosts
                weatherBoostList = checkAllWeatherTypesByType(weatherBoost, weatherBoostList, type, typePrimaryKey)
                typePrimaryKey++
            }
        }

        // Batch Insert
        pokemonDao.insertAllWeatherBoosts(*weatherBoostList.toTypedArray())
    }

    private suspend fun checkAllWeatherTypesByType(weatherBoost: RapidPokemonGoWeatherBoosts,
                                                   weatherBoostList: ArrayList<PokemonWeatherBoostsEntity>,
                                                   type: String,
                                                   currTypePrimaryKey: Long): ArrayList<PokemonWeatherBoostsEntity> {
        weatherBoost.Clear.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Clear"))
            }
        }
        weatherBoost.Cloudy.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Cloudy"))
            }
        }
        weatherBoost.Fog.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Fog"))
            }
        }
        weatherBoost.PartlyCloudy.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Partly Cloudy"))
            }
        }
        weatherBoost.Rain.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Rain"))
            }
        }
        weatherBoost.Snow.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Snow"))
            }
        }
        weatherBoost.Sunny.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Sunny"))
            }
        }
        weatherBoost.Wind.forEach { weatherItem ->
            if(weatherItem == type) {
                weatherBoostList.add(PokemonWeatherBoostsEntity(null, currTypePrimaryKey, "Wind"))
            }
        }
        return weatherBoostList
    }

    suspend fun updateDetailedPokemonData() {
        // Update with MaxCp
        updateMaxCp()

        // Update with Candy To Evolve
        updateCandyToEvolve()

        // Update Pokemon Buddy Distances
        updatePokemonBuddyDistances()
    }

    private suspend fun updateMaxCp() {
        val rapidPokemonGoMaxCp: ArrayList<RapidPokemonGoMaxCp> = ArrayList()
        rapidPokemonGoMaxCp.addAll(service.getRapidPokemonGoMaxCp())

        for(item in rapidPokemonGoMaxCp) {
            pokemonDao.updateMaxCp(item.MaxCp, item.pokemon_id)
        }
    }

    private suspend fun updateCandyToEvolve() {
        val rapidRapidPokemonGoCandyEvolve = service.getRapidPokemonGoCandyEvolve()
        for(item in rapidRapidPokemonGoCandyEvolve.OneHundred) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.Twelve) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.TwentyTwo) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.TwentyFive) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.ThreeHundredSixty) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.FourHundred) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.FortyFive) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.Fifty) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoCandyEvolve.Ninety) {
            pokemonDao.updateCandyToEvolve(item.CandyRequired, item.PokemonId.toInt())
        }
    }

    private suspend fun updatePokemonBuddyDistances() {
        val rapidRapidPokemonGoBuddyDistances = service.getRapidPokemonGoBuddyDistances()
        for(item in rapidRapidPokemonGoBuddyDistances.One) {
            pokemonDao.updateBuddyDistances(item.Distance, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoBuddyDistances.Three) {
            pokemonDao.updateBuddyDistances(item.Distance, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoBuddyDistances.Five) {
            pokemonDao.updateBuddyDistances(item.Distance, item.PokemonId.toInt())
        }
        for(item in rapidRapidPokemonGoBuddyDistances.Twenty) {
            pokemonDao.updateBuddyDistances(item.Distance, item.PokemonId.toInt())
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

    private fun flattenAllPokemonLists(liveDataList: List<PokemonFormsTypesWeatherBoosts>): List<PokemonFormsTypesWeatherBoosts> {
        val list = ArrayList<PokemonFormsTypesWeatherBoosts>()
        liveDataList.forEach { pokemonList ->
            var formsListIterator = 0
            val pokemonListForms = pokemonList.FORMS_LIST!!
            while(formsListIterator != pokemonListForms.size) {
                val pokemon = PokemonFormsTypesWeatherBoosts()
                // format of list is formId, formName
                val formId = pokemonListForms[formsListIterator]
                val formName = pokemonListForms[formsListIterator + 1]
                pokemon.FORMS_LIST!!.add(formId)
                pokemon.FORMS_LIST!!.add(formName)
                var typesListIterator = 0
                val pokemonListTypes = pokemonList.TYPES_LIST!!
                while(typesListIterator != pokemonListTypes.size) {
                    // format of list is formId, typeId, typeName
                    val formIdTypes = pokemonListTypes[typesListIterator]
                    val typeId = pokemonListTypes[typesListIterator+1]
                    val typeName = pokemonListTypes[typesListIterator+2]
                    if(formId == formIdTypes) {
                        pokemon.TYPES_LIST!!.add(typeId)
                        pokemon.TYPES_LIST!!.add(typeName)
                    }
                    var weatherListIterator = 0
                    val pokemonListWeather = pokemonList.WEATHER_LIST!!
                    while(weatherListIterator != pokemonListWeather.size) {
                        // format of list is typeId, weatherName
                        val typeIdWeathers = pokemonListWeather[weatherListIterator]
                        val weatherName = pokemonListWeather[weatherListIterator+1]
                        if(typeId == typeIdWeathers) {
                            pokemon.WEATHER_LIST!!.add(weatherName)
                        }
                        weatherListIterator += 2
                    }
                    typesListIterator += 3
                }
                formsListIterator += 2
                pokemon.pokemon_id = pokemonList.pokemon_id
                pokemon.base_attack = pokemonList.base_attack
                pokemon.base_defense = pokemonList.base_defense
                pokemon.base_stamina = pokemonList.base_stamina
                pokemon.max_cp = pokemonList.max_cp
                pokemon.pokemon_name = pokemonList.pokemon_name
                pokemon.candy_to_evolve = pokemonList.candy_to_evolve
                list.add(pokemon)
            }
        }
        return list
    }

    private suspend fun flattenPokemonLists(pokemon: PokemonFormsTypesWeatherBoosts) : PokemonFormsTypesWeatherBoosts {
        val flattenedPokemon = PokemonFormsTypesWeatherBoosts()
        // Format of FORMS_LIST is formId, formName
        // We only need form name for detailed views
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![1])

        var typesListIterator = 0
        val pokemonListTypes = pokemon.TYPES_LIST!!
        while(typesListIterator != pokemonListTypes.size) {
            // Format of list is formId, typeId, typeName
            // We only need typeName for detailed views
            flattenedPokemon.TYPES_LIST!!.add(pokemonListTypes[typesListIterator+2])
            typesListIterator += 3
        }

        var weatherListIterator = 0
        val pokemonListWeather = pokemon.WEATHER_LIST!!
        while(weatherListIterator != pokemonListWeather.size) {
            // format of list is typeId, weatherName
            // We only need weatherName for detailed views
            flattenedPokemon.WEATHER_LIST!!.add(pokemonListWeather[weatherListIterator+1])
            weatherListIterator += 2
        }

        flattenedPokemon.pokemon_id = pokemon.pokemon_id
        flattenedPokemon.base_attack = pokemon.base_attack
        flattenedPokemon.base_defense = pokemon.base_defense
        flattenedPokemon.base_stamina = pokemon.base_stamina
        flattenedPokemon.max_cp = pokemon.max_cp
        flattenedPokemon.pokemon_name = pokemon.pokemon_name
        flattenedPokemon.candy_to_evolve = pokemon.candy_to_evolve
        flattenedPokemon.buddy_distances = pokemon.buddy_distances
        return flattenedPokemon
    }
}