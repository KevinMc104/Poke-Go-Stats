package com.analytics.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/raid_exclusive_pokemon.json
 *
 * Returns a JSON dict with the keys being the Pokemon ID,
 * the values are an array containing the Pokemon name, ID,
 * and level raid they can be found in.
 * Currently all raid exclusive pokemon are tied to a specific raid level.
 * In the future the API might need to change if this changes.
 */
data class RapidPokemonGoRaidExclusive(
    val RaidLevel: Int,
    val Id: String
)