package com.example.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_buddy_distances.json
 *
 * When you make a Pokemon your buddy after a certain distance walked with
 * them you will get 1 candy from them. This distance depends on the specific Pokemon.
 * The current buddy distances are 1, 3, 5, and 20 kilometres distance. Returns a JSON object
 * where each key is the distance needed to gain a candy and the value is a list containing
 * multiple objects holding distance, Pokemon ID, Pokemon name and optionally the form.
 */
data class RapidPokemonGoBuddyDistances(
    val Distance: String,
    val PokemonId: String
)