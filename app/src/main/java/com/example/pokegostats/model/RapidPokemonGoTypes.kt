package com.example.pokegostats.model

/**
 * These models are based off the API response
 */
data class RapidPokemonGoTypes(
    val PokemonId: Int,
    val PokemonName: String,
    val Type: List<String>
)