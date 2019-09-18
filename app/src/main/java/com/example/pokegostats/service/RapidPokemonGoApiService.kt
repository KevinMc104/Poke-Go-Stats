package com.example.pokegostats.service

import com.example.pokegostats.model.*
import retrofit2.Response
import retrofit2.http.GET

interface RapidPokemonGoApiService {

    @GET("/pokemon_stats.json")
    suspend fun getRapidPokemonGoStats() : Response<List<RapidPokemonGoStats>>

    @GET("/pokemon_types.json")
    suspend fun getRapidPokemonGoTypes() : Response<List<RapidPokemonGoTypes>>

    @GET("/pokemon_max_cp.json")
    suspend fun getRapidPokemonGoMaxCp() : Response<List<RapidPokemonGoMaxCp>>

    @GET("/pokemon_candy_to_evolve.json")
    suspend fun getRapidPokemonGoCandyEvolve() : Response<HashMap<String, List<RapidPokemonGoCandyEvolve>>>

    @GET("/pokemon_buddy_distances.json")
    suspend fun getRapidPokemonGoBuddyDistances() : Response<HashMap<String, List<RapidPokemonGoBuddyDistances>>>

    @GET("/raid_exclusive_pokemon.json")
    suspend fun getRapidPokemonGoRaidExclusive() : Response<HashMap<String, RapidPokemonGoRaidExclusive>>

    @GET("/nesting_pokemon.json")
    suspend fun getRapidPokemonGoNestingPokemon() : Response<HashMap<String, RapidPokemonGoNestingPokemon>>

    @GET("/shiny_pokemon.json")
    suspend fun getRapidPokemonGoShinyPokemon() : Response<HashMap<String, RapidPokemonGoShinyPokemon>>

    @GET("/released_pokemon.json")
    suspend fun getRapidPokemonGoReleasedPokemon() : Response<HashMap<String, RapidPokemonGoReleasedPokemon>>

    @GET("/possible_ditto_pokemon.json")
    suspend fun getRapidPokemonGoPossibleDittoTypes() : Response<HashMap<String, RapidPokemonGoPossibleDittoTypes>>

    @GET("/pokemon_encounter_data.json")
    suspend fun getRapidPokemonGoEncounterData() : Response<List<RapidPokemonGoEncounterData>>

    @GET("/fast_moves.json")
    suspend fun getRapidPokemonGoFastMoves() : Response<List<RapidPokemonGoFastMoves>>

    @GET("/charged_moves.json")
    suspend fun getRapidPokemonGoChargedMoves() : Response<List<RapidPokemonGoChargedMoves>>

    @GET("/weather_boosts.json")
    suspend fun getRapidPokemonGoWeatherBoosts() : Response<HashMap<String, List<String>>>
}