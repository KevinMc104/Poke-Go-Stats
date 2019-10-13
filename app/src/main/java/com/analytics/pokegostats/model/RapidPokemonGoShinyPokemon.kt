package com.analytics.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/shiny_pokemon.json
 *
 * Returns a JSON dict with the keys being the Pokemon ID,
 * the values are an array containing the Pokemon name, ID,
 * and how that shiny can be found.
 * There are four main keys determining how the shiny can be found:
 * foundwild - True if the shiny Pokemon is found in the wild
 * foundraid - True if the shiny Pokemon was once in, or currently is in raids as a shiny possibility
 * foundegg - True if the shiny Pokemon can be hatched from an egg as a shiny
 * foundevolution - True if the Pokemon can be evolved from another shiny Pokemon
 */
data class RapidPokemonGoShinyPokemon(
    val FoundEgg: Boolean,
    val FoundEvolution: Boolean,
    val FoundRaid: Boolean,
    val FoundWild: Boolean,
    val Id: String
)