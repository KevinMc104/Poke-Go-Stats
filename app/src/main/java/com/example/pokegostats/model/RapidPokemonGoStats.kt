package com.example.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_stats.json
 *
 * Each Pokemon has three base stats, attack, defense and stamina which
 * determine how innately strong it is in each of these areas.
 * These effect how much HP and damage each move can do along with its
 * level and the typing of the moves. Returns a JSON array where each element
 * is a dict containing the pokemon name, ID, base stamina, base attack, and base defense.
 */
data class RapidPokemonGoStats(
    val BaseAttack: Int,
    val BaseDefense: Int,
    val BaseStamina: Int,
    val PokemonId: Int,
    val PokemonName: String,
    val Form: String
)
