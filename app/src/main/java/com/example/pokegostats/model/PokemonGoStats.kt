package com.example.pokegostats.model

data class PokemonGoStatsResult(
    val items: List<PokemonGoStats>
)

data class PokemonGoStats(
    val base_attack: Int,
    val base_defense: Int,
    val base_stamina: Int,
    val pokemon_id: Int,
    val pokemon_name: String
)
