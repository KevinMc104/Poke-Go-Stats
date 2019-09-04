package com.example.pokegostats.model

data class PokemonAdapterModel (
    val PokemonName: String,
    val MaxCp: String,
    val Type: ArrayList<String> = ArrayList()
)