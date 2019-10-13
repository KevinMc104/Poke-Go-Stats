package com.analytics.pokegostats.model

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_encounter_data.json
 *
 * When encountering a Pokemon there are a number of metrics that
 * influence the catch rate, and what it does during the encounter.
 * This API groups all the information that influences an encounter together.
 * Returns a JSON array where each element is a dict containing
 * attackprobability, basecapturerate, basefleerate, dodgeprobability,
 * maxpokemonactionfrequency, minpokemonactionfrequency, Pokemon ID, Pokemon name and optionally the form.
 *
 * The fields attackprobability, basecapturerate, basefleerate, and dodgeprobability are a value from 0 to 1.
 * 0 represents 0% chance of it ocurring each turn and 1 represents 100% chance.
 *
 * The fields maxpokemonactionfrequency, and minpokemonactionfrequency are in
 * seconds representing the maximum and minimum amount of time between an action.
 *
 * Each of these is different depending on the Form
 */
data class RapidPokemonGoEncounterData(
    val AttackProbability: Double,
    val BaseCaptureRate: Double,
    val BaseFleeRate: Double,
    val DodgeProbability: Double,
    val MaxPokemonActionFrequency: Double,
    val MinPokemonActionFrequency: Double,
    val Form: String,
    val PokemonId: Int
)
