package com.example.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_buddy_distances.json
 *
 * Only specific Pokemon will nest and this API lets you get the name and
 * ID of all Pokemon known to currently nest.
 * Returns a JSON dict with the keys being the Pokemon ID,
 * the values are an array containing the pokemon name and ID.
 */
data class RapidPokemonGoNestingPokemon(
    val Id: String
)