package com.example.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_max_cp.json
 *
 * Each Pokemon has a maximum CP that it may achieve if it is a
 * perfect pokemon (with 15 attack, stamina, and defense) and is level 40.
 * This API returns the maximum CP for each Pokemon.
 * Returns a JSON array where each element is a dict containing the
 * pokemon name, ID, and maximum CP.
 */
data class RapidPokemonGoMaxCp(
    val MaxCp: Int,
    val pokemon_id: Int
)
