package com.example.pokegostats.pokemon

import com.example.pokegostats.service.PokemonGoApiService
import javax.inject.Inject

class Pokemon @Inject constructor(val service: PokemonGoApiService){
    var pokemonList: ArrayList<String> = ArrayList()

    fun addPokemon(){

    }
}
