package com.example.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/released_pokemon.json
 *
 * Currently in Pokemon Go the majority of Pokemon have been released from
 * the first three generations of Pokemon games.
 * This API lets you get the full list of Pokemon that are currently released.
 * Returns a json dict with the keys being the pokemon ID, the values are an array
 * containing the pokemon name and ID.
 */
data class RapidPokemonGoReleasedPokemon(
    val Id: Int
)
