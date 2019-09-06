package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.pokegostats.room.entity.PokemonAndPokemonTypes

@Dao
interface PokemonAndPokemonTypesDao {
    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemonAndPokemonTypes(): LiveData<List<PokemonAndPokemonTypes>>
}