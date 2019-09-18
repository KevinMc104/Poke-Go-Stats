package com.example.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_candy_to_evolve.json
 *
 * For all Pokemon that evolve there will be a specific candy requirement.
 * This API groups the evolvable Pokemon into the specific candy requirements for each.
 * Returns a JSON object where each key is the amount of candy needed to evolve and the
 * value is a list containing multiple objects holding candy needed to evolve, Pokemon ID,
 * Pokemon name and optionally the form.
 */
data class RapidPokemonGoCandyEvolve(
    val CandyRequired: String,
    val PokemonId: String
)
