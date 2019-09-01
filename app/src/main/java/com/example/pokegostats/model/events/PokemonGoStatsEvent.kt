package com.example.pokegostats.model.events

import com.example.pokegostats.model.PokemonGoStats

data class RapidPokemonGoStatsEvent(
    val response: PokemonGoStats? = null,
    val isSuccess: Boolean,
    val error: String = ""
)