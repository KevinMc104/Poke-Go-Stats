package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.pokegostats.room.entity.PokemonAndPokemonForms

@Dao
interface PokemonAndPokemonFormsDao {
    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemonAndPokemonForms(): LiveData<List<PokemonAndPokemonForms>>
}