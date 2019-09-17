package com.example.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_types.json
 *
 * All Pokemon have either one or two types, these types affect the strength
 * of moves and weaknesses it has to opponents moves. This API lists the types of each
 * Pokemon. Returns a JSON array where each element is a dict containing
 * type (an array of one or two items), Pokemon ID, Pokemon name and optionally the form.
 */
data class RapidPokemonGoTypes(
    val Form: String?,
    val PokemonId: Int,
    val PokemonName: String,
    val Type: List<String>
)