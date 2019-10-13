package com.analytics.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/possible_ditto_pokemon.json
 *
 * Ditto is a Pokemon that can transform into any Pokemon.
 * In the wild you find him by catching a Pokemon which then transforms into Ditto.
 * You cant tell which Pokemon might turn into a Ditto however
 * if someone has caught a Ditto it will be a Ditto for everyone.
 * This API allows you to get the list of Pokemon which are potentially a Ditto.
 * Returns a JSON dict with the keys being the Pokemon ID, the values are an
 * array containing the pokemon name and ID.
 */
data class RapidPokemonGoPossibleDittoTypes(
    val Id: Int
)
