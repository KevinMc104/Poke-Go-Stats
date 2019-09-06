package com.example.pokegostats.model

/**
 * These models are based off the API response
 */
data class RapidPokemonGoStats(
    val BaseAttack: Int,
    val BaseDefense: Int,
    val BaseStamina: Int,
    val PokemonId: Int,
    val PokemonName: String
)
