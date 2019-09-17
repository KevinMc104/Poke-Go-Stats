package com.example.pokegostats.model

/**
 * These models are based off the API response
 */
data class RapidPokemonGoTypes(
    val Form: String?,
    val PokemonId: Int,
    val PokemonName: String,
    val Type: List<String>
)