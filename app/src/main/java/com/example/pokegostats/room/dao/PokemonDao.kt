package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.pokegostats.room.entity.*

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table ORDER BY pokemon_id")
    fun getAllPokemon(): LiveData<List<PokemonEntity>>

    @Query("SELECT *, " +
            "(SELECT " +
                "GROUP_CONCAT(pokemon_forms.form_id || ',' || pokemon_forms.form_name) " +
                "FROM pokemon_forms " +
                    "WHERE pokemon_table.pokemon_id = pokemon_forms.pokemon_uid) AS FORMS_LIST, " +
            "(SELECT " +
                "GROUP_CONCAT(pokemon_types.form_uid || ',' || pokemon_types.type_id || ',' || pokemon_types.type_name) " +
                "FROM pokemon_forms " +
                    "INNER JOIN pokemon_types " +
                        "ON pokemon_forms.form_id = pokemon_types.form_uid " +
                "WHERE pokemon_types.form_uid = pokemon_forms.form_id " +
                    "AND pokemon_table.pokemon_id = pokemon_forms.pokemon_uid) AS TYPES_LIST, " +
            "(SELECT " +
                "GROUP_CONCAT(pokemon_weather_boosts.type_uid || ',' || pokemon_weather_boosts.weather_name) " +
                "FROM pokemon_weather_boosts " +
                "INNER JOIN pokemon_types " +
                    "ON pokemon_types.type_id = pokemon_weather_boosts.type_uid " +
                "INNER JOIN pokemon_forms " +
                    "ON pokemon_forms.form_id = pokemon_types.form_uid " +
                "WHERE pokemon_types.type_id = pokemon_weather_boosts.type_uid " +
                    "AND pokemon_types.form_uid = pokemon_forms.form_id " +
                    "AND pokemon_table.pokemon_id = pokemon_forms.pokemon_uid) AS WEATHER_LIST " +
            "FROM pokemon_table")
    fun getAllPokemonFormsTypesWeatherBoosts(): LiveData<List<PokemonFormsTypesWeatherBoosts>>

    @Update
    suspend fun update(vararg pokemon: PokemonEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllPokemon(vararg pokemon: PokemonEntity)

    @Insert
    suspend fun insertAllPokemonForms(vararg forms: PokemonFormEntity)

    @Query("INSERT INTO pokemon_types (type_id, form_uid, type_name) VALUES(:typeId, (SELECT form_id FROM pokemon_forms WHERE form_name = :formName AND pokemon_uid = :pokemonId), :typeName)")
    suspend fun insertType(typeId: Long, pokemonId: Int, formName: String, typeName: String)

    @Insert
    suspend fun insertAllWeatherBoosts(vararg types: PokemonWeatherBoostsEntity)

    @Query("UPDATE pokemon_table SET max_cp = :maxCp WHERE pokemon_id = :pokemonId")
    suspend fun updateMaxCp(maxCp: Int, pokemonId: Int)

    @Query("UPDATE pokemon_table SET candy_to_evolve = :candyToEvolve WHERE pokemon_id = :pokemonId")
    suspend fun updateCandyToEvolve(candyToEvolve: String, pokemonId: Int)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAll()
}