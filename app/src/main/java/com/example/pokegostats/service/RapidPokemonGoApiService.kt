package com.example.pokegostats.service

import com.example.pokegostats.model.*
import retrofit2.Response
import retrofit2.http.GET

interface RapidPokemonGoApiService {

    @GET("/pokemon_stats.json")
    suspend fun getRapidPokemonGoStats() : Response<List<RapidPokemonGoStats>>

    @GET("/pokemon_types.json")
    suspend fun getRapidPokemonGoTypes() : Response<List<RapidPokemonGoTypes>>

    @GET("/weather_boosts.json")
    suspend fun getRapidPokemonGoWeatherBoosts() : Response<RapidPokemonGoWeatherBoosts>

    @GET("/pokemon_max_cp.json")
    suspend fun getRapidPokemonGoMaxCp() : Response<List<RapidPokemonGoMaxCp>>

    @GET("/fast_moves.json")
    suspend fun getRapidPokemonGoFastMoves() : Response<List<RapidPokemonGoFastMoves>>

    @GET("/charged_moves.json")
    suspend fun getRapidPokemonGoChargedMoves() : Response<List<RapidPokemonGoChargedMoves>>

    // TODO: Implement other Endpoints
}