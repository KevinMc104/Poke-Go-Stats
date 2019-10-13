package com.analytics.pokegostats.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.analytics.pokegostats.model.*
import com.analytics.pokegostats.room.dao.DateCacheDao
import com.analytics.pokegostats.room.dao.PokemonDao
import com.analytics.pokegostats.room.dao.PokemonMovesDao
import com.analytics.pokegostats.room.entity.*
import com.analytics.pokegostats.service.PokemonGoApiService
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PokemonGoStatsRepository @Inject constructor(val pokemonDao: PokemonDao,
                                                   val pokemonMovesDao: PokemonMovesDao,
                                                   val dateCacheDao: DateCacheDao,
                                                   val service: PokemonGoApiService) {
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

    private suspend fun dataIsOld(): Boolean {
        var currDateOlder = true
        // Check to see if entity is populated before getting date
        if(dateCacheDao.checkDateTable() > 0) {
            // There should only ever be one entry in this date table
            val storedDateList = dateCacheDao.getDateData()
            val storedDate = storedDateList[0].dateTime

            // NOTE: Use Java Time when project is updated to newer API's
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR_OF_DAY, -24)
            val oneDayAgo = calendar.time

            // Check if the Current Stored Date is older than 24 hours
            currDateOlder = storedDate!! < oneDayAgo
        }
        return currDateOlder
    }

    suspend fun insertAllData(retryCalls: Boolean) {
        // Delete data and insert if older than 1 day or if date table is empty
        if(dataIsOld() || retryCalls) {
            // Wipe Old Data
            pokemonDao.deleteAll()
            pokemonMovesDao.deleteAll()
            dateCacheDao.deleteAll()

            // Add Pokemon
            insertPokemonStats()

            // Add Types and Weather Boosts
            insertPokemonTypesAndWeatherBoosts()

            // Insert Moves
            insertMoves()

            // Insert Detailed Stats
            updateDetailedPokemonData()

            // Insert a new timestamp
            dateCacheDao.insertAllDateCacheEntries(DateCacheEntity(null, Calendar.getInstance().time))
        }
        // Do not call out to RapidAPI to insert if the data is newer than 1 day
    }

    private suspend fun insertPokemonStats() {
        val rapidPokemonGoStats: ArrayList<RapidPokemonGoStats> = ArrayList()
        rapidPokemonGoStats.addAll(service.getRapidPokemonGoStats())

        val pokemonListToBeInserted = ArrayList<PokemonEntity>()
        val pokemonFormsListToBeInserted = ArrayList<PokemonFormEntity>()
        var formsPrimaryKey = 1L
        for(item in rapidPokemonGoStats) {
            pokemonListToBeInserted.add(PokemonEntity(item.PokemonId, item.BaseAttack, item.BaseDefense,
                                    item.BaseStamina, null, item.PokemonName,null,
                                    null, 0, 0, 0,
                                    0, 0, 0, 0,
                                    0, 0))
            if(item.Form.isNullOrBlank()) {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(formsPrimaryKey, item.PokemonId, "Default",
                    0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0))
            } else {
                pokemonFormsListToBeInserted.add(PokemonFormEntity(formsPrimaryKey, item.PokemonId, item.Form,
                    0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0))
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
        val weatherBoosts = service.getRapidPokemonGoWeatherBoosts()

        var weatherBoostsList = ArrayList<PokemonWeatherBoostsEntity>()
        var typePrimaryKey = 1L
        rapidPokemonGoTypes.forEach { pokemon ->
            pokemon.Type.forEach { type ->
                if(pokemon.Form.isNullOrBlank()) {
                    pokemonDao.insertType(typePrimaryKey, pokemon.PokemonId, "Default", type)
                } else {
                    pokemonDao.insertType(typePrimaryKey, pokemon.PokemonId, pokemon.Form, type)
                }
                // Check if Type is matched to any number of weather boosts
                weatherBoosts.forEach {(key, value) ->
                    for(item in value) {
                        if(item == type) {
                            weatherBoostsList.add(PokemonWeatherBoostsEntity(null, typePrimaryKey, key))
                        }
                    }
                }
                typePrimaryKey++
            }
        }

        // Batch Insert
        pokemonDao.insertAllWeatherBoosts(*weatherBoostsList.toTypedArray())
    }

    private suspend fun updateDetailedPokemonData() {
        // Update with MaxCp
        updateMaxCp()

        // Update with Candy To Evolve
        updateCandyToEvolve()

        // Update Pokemon Buddy Distances
        updatePokemonBuddyDistances()

        // Update Raid Exclusive Pokemon
        updatePokemonRaidExclusive()

        // Update Nesting Pokemon
        updateNestingPokemon()

        // Update Shiny Pokemon
        updateShinyPokemon()

        // Update Released Pokemon
        updateReleasedPokemon()

        // Update Possible Ditto Pokemon
        updatePossibleDittoTypes()

        // Update Pokemon Encounter Data
        updatePokemonEncounterData()
    }

    private suspend fun updateMaxCp() {
        val rapidPokemonGoMaxCp: ArrayList<RapidPokemonGoMaxCp> = ArrayList()
        rapidPokemonGoMaxCp.addAll(service.getRapidPokemonGoMaxCp())

        for(item in rapidPokemonGoMaxCp) {
            pokemonDao.updateMaxCp(item.MaxCp, item.pokemon_id)
        }
    }

    private suspend fun updateCandyToEvolve() {
        val results = service.getRapidPokemonGoCandyEvolve()
        results.forEach {(key, value) ->
            value.forEach{ pokemon ->
                pokemonDao.updateCandyToEvolve(pokemon.CandyRequired, pokemon.PokemonId.toInt())
            }
        }
    }

    private suspend fun updatePokemonBuddyDistances() {
        val results = service.getRapidPokemonGoBuddyDistances()
        results.forEach {(key, value) ->
            value.forEach{ pokemon ->
                pokemonDao.updateBuddyDistances(pokemon.Distance, pokemon.PokemonId.toInt())
            }
        }
    }

    private suspend fun updatePokemonRaidExclusive() {
        val results = service.getRapidPokemonGoRaidExclusive()
        results.forEach {(key, value) ->
            pokemonDao.updateRaidExclusive(1, value.RaidLevel, value.Id.toInt())
        }
    }

    private suspend fun updateNestingPokemon() {
        val results = service.getRapidPokemonGoNestingPokemon()
        results.forEach {(key, value) ->
            pokemonDao.updateNestingPokemon(1, value.Id.toInt())
        }
    }

    private suspend fun updateShinyPokemon() {
        val results = service.getRapidPokemonGoShinyPokemon()
        results.forEach {(key, value) ->
            var foundEgg = 0
            if(value.FoundEgg) foundEgg = 1

            var foundEvolution = 0
            if(value.FoundEvolution) foundEvolution = 1

            var foundRaid = 0
            if(value.FoundRaid) foundRaid = 1

            var foundWild = 0
            if(value.FoundWild) foundWild = 1
            pokemonDao.updateShinyPokemon(foundEgg, foundEvolution, foundRaid, foundWild, value.Id.toInt())
        }
    }

    private suspend fun updateReleasedPokemon() {
        val results = service.getRapidPokemonGoReleasedPokemon()
        results.forEach {(key, value) ->
            pokemonDao.updateReleasedPokemon(1, value.Id)
        }
    }

    private suspend fun updatePossibleDittoTypes() {
        val results = service.getRapidPokemonGoPossibleDittoTypes()
        results.forEach {(key, value) ->
            pokemonDao.updatePossibleDittoTypes(1, value.Id)
        }
    }

    private suspend fun updatePokemonEncounterData() {
        val results = service.getRapidPokemonGoEncounterData()
        for(item in results) {
            // Convert Decimals to Percents
            val attackProb = item.AttackProbability * 100
            val baseCapRate = item.BaseCaptureRate * 100
            val baseFleeRate = item.BaseFleeRate * 100
            val dodgeProb = item.DodgeProbability * 100
            pokemonDao.updatePokemonEncounterData(attackProb, baseCapRate, baseFleeRate, dodgeProb,
                item.MaxPokemonActionFrequency, item.MinPokemonActionFrequency,
                item.Form, item.PokemonId)
        }
    }

    private suspend fun insertMoves() {
        var list = getFastMoves()
        list = getChargedMoves(list)

        pokemonMovesDao.insertAll(*list.toTypedArray())
    }

    private suspend fun getFastMoves(): ArrayList<PokemonMovesEntity> {
        val moves: ArrayList<RapidPokemonGoFastMoves> = ArrayList()
        moves.addAll(service.getRapidPokemonGoFastMoves())

        val list = ArrayList<PokemonMovesEntity>()
        for(item in moves) {
            list.add(PokemonMovesEntity(item.Name, item.Duration, 0.0, item.EnergyDelta, item.Power, item.StaminaLossScaler, item.Type))
        }
        return list
    }

    private suspend fun getChargedMoves(list: ArrayList<PokemonMovesEntity>): ArrayList<PokemonMovesEntity> {
        val moves: ArrayList<RapidPokemonGoChargedMoves> = ArrayList()
        moves.addAll(service.getRapidPokemonGoChargedMoves())

        for(item in moves) {
            // Convert to a percent
            if(!item.CriticalChance.isNullOrBlank()) {
                val criticalChance = item.CriticalChance.toDouble() * 100
                list.add(PokemonMovesEntity(item.Name, item.Duration, criticalChance, item.EnergyDelta, item.Power, item.StaminaLossScaler, item.Type))
            } else {
                list.add(PokemonMovesEntity(item.Name, item.Duration, 0.0, item.EnergyDelta, item.Power, item.StaminaLossScaler, item.Type))
            }
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
                // format of FORMS_LIST is formId, formName, attack_probability, base_capture_rate, base_flee_rate,
                // dodge_probability, min_pokemon_action_frequency, max_pokemon_action_frequency
                val formId = pokemonListForms[formsListIterator]
                val formName = pokemonListForms[formsListIterator + 1]
                val attackProbability = pokemonListForms[formsListIterator + 2]
                val baseCaptureRate = pokemonListForms[formsListIterator + 3]
                val baseFleeRate = pokemonListForms[formsListIterator + 4]
                val dodgeProbability = pokemonListForms[formsListIterator + 5]
                val minPokemonActionFrequency = pokemonListForms[formsListIterator + 6]
                val maxPokemonActionFrequency = pokemonListForms[formsListIterator + 7]
                pokemon.FORMS_LIST!!.add(formId)
                pokemon.FORMS_LIST!!.add(formName)
                pokemon.FORMS_LIST!!.add(attackProbability)
                pokemon.FORMS_LIST!!.add(baseCaptureRate)
                pokemon.FORMS_LIST!!.add(baseFleeRate)
                pokemon.FORMS_LIST!!.add(dodgeProbability)
                pokemon.FORMS_LIST!!.add(minPokemonActionFrequency)
                pokemon.FORMS_LIST!!.add(maxPokemonActionFrequency)
                var typesListIterator = 0
                val pokemonListTypes = pokemonList.TYPES_LIST!!
                while(typesListIterator != pokemonListTypes.size) {
                    // format of TYPES_LIST is formId, typeId, typeName
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
                        // format of WEATHER_LIST is typeId, weatherName
                        val typeIdWeathers = pokemonListWeather[weatherListIterator]
                        val weatherName = pokemonListWeather[weatherListIterator+1]
                        if(typeId == typeIdWeathers) {
                            pokemon.WEATHER_LIST!!.add(weatherName)
                        }
                        weatherListIterator += 2
                    }
                    typesListIterator += 3
                }
                formsListIterator += 8
                pokemon.pokemon_id = pokemonList.pokemon_id
                pokemon.base_attack = pokemonList.base_attack
                pokemon.base_defense = pokemonList.base_defense
                pokemon.base_stamina = pokemonList.base_stamina
                pokemon.max_cp = pokemonList.max_cp
                pokemon.pokemon_name = pokemonList.pokemon_name
                pokemon.candy_to_evolve = pokemonList.candy_to_evolve
                pokemon.buddy_distances = pokemonList.buddy_distances
                pokemon.raid_exclusive = pokemonList.raid_exclusive
                pokemon.raid_level = pokemonList.raid_level
                pokemon.nested_pokemon = pokemonList.nested_pokemon
                pokemon.shiny_found_egg = pokemonList.shiny_found_egg
                pokemon.shiny_found_evolution = pokemonList.shiny_found_evolution
                pokemon.shiny_found_raid = pokemonList.shiny_found_raid
                pokemon.shiny_found_wild = pokemonList.shiny_found_wild
                pokemon.released_pokemon = pokemonList.released_pokemon
                pokemon.possible_ditto = pokemonList.possible_ditto
                list.add(pokemon)
            }
        }
        return list
    }

    private suspend fun flattenPokemonLists(pokemon: PokemonFormsTypesWeatherBoosts) : PokemonFormsTypesWeatherBoosts {
        val flattenedPokemon = PokemonFormsTypesWeatherBoosts()
        // Format of FORMS_LIST is formId, formName, attack_probability, base_capture_rate, base_flee_rate,
        // dodge_probability, min_pokemon_action_frequency, max_pokemon_action_frequency
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![1])
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![2])
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![3])
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![4])
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![5])
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![6])
        flattenedPokemon.FORMS_LIST!!.add(pokemon.FORMS_LIST!![7])

        var typesListIterator = 0
        val pokemonListTypes = pokemon.TYPES_LIST!!
        while(typesListIterator != pokemonListTypes.size) {
            // Format of TYPES_LIST is formId, typeId, typeName
            // We only need typeName for detailed views
            flattenedPokemon.TYPES_LIST!!.add(pokemonListTypes[typesListIterator+2])
            typesListIterator += 3
        }

        var weatherListIterator = 0
        val pokemonListWeather = pokemon.WEATHER_LIST!!
        while(weatherListIterator != pokemonListWeather.size) {
            // format of WEATHER_LIST is typeId, weatherName
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
        flattenedPokemon.raid_exclusive = pokemon.raid_exclusive
        flattenedPokemon.raid_level = pokemon.raid_level
        flattenedPokemon.nested_pokemon = pokemon.nested_pokemon
        flattenedPokemon.shiny_found_egg = pokemon.shiny_found_egg
        flattenedPokemon.shiny_found_evolution = pokemon.shiny_found_evolution
        flattenedPokemon.shiny_found_raid = pokemon.shiny_found_raid
        flattenedPokemon.shiny_found_wild = pokemon.shiny_found_wild
        flattenedPokemon.released_pokemon = pokemon.released_pokemon
        flattenedPokemon.possible_ditto = pokemon.possible_ditto
        return flattenedPokemon
    }
}