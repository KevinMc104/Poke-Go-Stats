package com.example.pokegostats.model

data class PokemonGoStatsResult(
    val Items: List<PokemonGoStats>
)

data class PokemonGoStats(
    val BaseAttack: Int,
    val BaseDefense: Int,
    val BaseStamina: Int,
    val PokemonId: Int,
    val PokemonName: String
)
