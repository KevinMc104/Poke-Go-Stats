package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes
import com.example.pokegostats.room.entity.PokemonEntity

@Dao
interface PokemonAndFormsAndTypesDao {
    @Query("SELECT * FROM pokemon_table INNER JOIN pokemon_forms ON pokemon_table.pokemon_id = pokemon_forms.pokemon_uid")
    fun getAllPokemonAndFormsAndTypes(): LiveData<List<PokemonAndFormsAndTypes>>

    @Query("SELECT * FROM pokemon_table INNER JOIN pokemon_forms ON pokemon_table.pokemon_id = pokemon_forms.pokemon_uid WHERE pokemon_id = :pokemonId AND form_name = :pokemonFormName")
    suspend fun getPokemon(pokemonId: Int, pokemonFormName: String): PokemonAndFormsAndTypes
}