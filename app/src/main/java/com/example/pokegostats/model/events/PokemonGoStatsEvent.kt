package com.example.pokegostats.model.events

import com.example.pokegostats.model.PokemonGoStatsResult

data class RapidPokemonGoStatsEvent(
    val response: PokemonGoStatsResult? = null,
    val isSuccess: Boolean,
    val error: String = ""
)