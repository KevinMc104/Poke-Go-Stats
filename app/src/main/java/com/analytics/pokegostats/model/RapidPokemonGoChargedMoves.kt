package com.analytics.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/charged_moves.json
 *
 * Each Pokemon has a fast and charged move.
 * This API allows you to download the full list of charged moves in the current
 * Pokemon Go game master. Returns a JSON array where each element is a dict containing
 * the staminalossscaler, name, power, duration, criticalchance, energydelta and type.
 */
data class RapidPokemonGoChargedMoves(
    val CriticalChance: String,
    val Duration: Int,
    val EnergyDelta: Int,
    val Name: String,
    val Power: Int,
    val StaminaLossScaler: String,
    val Type: String
)
