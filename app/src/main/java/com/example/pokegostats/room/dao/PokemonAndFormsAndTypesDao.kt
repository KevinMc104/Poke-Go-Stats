package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes

@Dao
interface PokemonAndFormsAndTypesDao {
    @Query("SELECT * FROM pokemon_table INNER JOIN pokemon_forms ON pokemon_table.pokemon_id = pokemon_forms.pokemon_uid")
    fun getAllPokemonAndFormsAndTypes(): LiveData<List<PokemonAndFormsAndTypes>>
}