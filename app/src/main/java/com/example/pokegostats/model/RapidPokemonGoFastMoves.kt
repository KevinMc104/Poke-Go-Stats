package com.example.pokegostats.model

/**
 * These models are based off the API response
 */
data class RapidPokemonGoFastMoves(
    val Duration: Int,
    val EnergyDelta: Int,
    val Name: String,
    val Power: Int,
    val StaminaLossScaler: String,
    val Type: String
)
