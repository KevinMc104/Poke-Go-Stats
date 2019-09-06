package com.example.pokegostats.service

import com.example.pokegostats.model.RapidPokemonGoStats
import com.example.pokegostats.model.RapidPokemonGoTypes
import retrofit2.Response
import retrofit2.http.GET

interface RapidPokemonGoApiService {

    @GET("/pokemon_stats.json")
    suspend fun getRapidPokemonGoStats() : Response<List<RapidPokemonGoStats>>

    @GET("/pokemon_types.json")
    suspend fun getRapidPokemonGoTypes() : Response<List<RapidPokemonGoTypes>>

    // TODO: Implement other Endpoints
}