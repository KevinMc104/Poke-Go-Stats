package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.pokegostats.room.entity.PokemonFormsAndPokemonTypes

@Dao
interface PokemonFormsAndPokemonTypesDao {
    @Query("SELECT * FROM pokemon_forms")
    fun getAllPokemonFormsAndPokemonTypes(): LiveData<List<PokemonFormsAndPokemonTypes>>
}