package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes

@Dao
interface PokemonAndFormsAndTypesDao {
    @Query("SELECT * FROM pokemon_table " +
            "INNER JOIN pokemon_forms " +
                "ON pokemon_table.pokemon_id = pokemon_forms.pokemon_uid " +
            "INNER JOIN pokemon_types " +
                "ON pokemon_forms.form_id = pokemon_types.form_uid " +
            "INNER JOIN pokemon_weather_boosts " +
                "ON pokemon_types.type_id = pokemon_weather_boosts.type_uid")
    fun getAllPokemonAndFormsAndTypes(): LiveData<List<PokemonAndFormsAndTypes>>

    @Query("SELECT * FROM pokemon_table " +
            "INNER JOIN pokemon_forms " +
                "ON pokemon_table.pokemon_id = pokemon_forms.pokemon_uid " +
            "INNER JOIN pokemon_types " +
                "ON pokemon_forms.form_id = pokemon_types.form_uid " +
            "INNER JOIN pokemon_weather_boosts " +
                "ON pokemon_types.type_id = pokemon_weather_boosts.type_uid " +
            "WHERE pokemon_id = :pokemonId AND form_name = :pokemonFormName")
    suspend fun getPokemon(pokemonId: Int, pokemonFormName: String): PokemonAndFormsAndTypes
}