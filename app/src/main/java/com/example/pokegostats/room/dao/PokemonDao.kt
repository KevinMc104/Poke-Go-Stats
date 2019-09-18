package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonFormEntity
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.example.pokegostats.room.entity.PokemonWeatherBoostsEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table ORDER BY pokemon_id")
    fun getAllPokemon(): LiveData<List<PokemonEntity>>

    @Query("SELECT *, " +
            "(SELECT " +
                "GROUP_CONCAT(pokemon_forms.form_id || ',' || pokemon_forms.form_name || ',' || " +
                    "pokemon_forms.attack_probability || ',' || pokemon_forms.base_capture_rate || ',' || " +
                    "pokemon_forms.base_flee_rate || ',' || pokemon_forms.dodge_probability || ',' || " +
                    "pokemon_forms.max_pokemon_action_frequency || ',' || pokemon_forms.min_pokemon_action_frequency) " +
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

    @Query("SELECT *, " +
            "(SELECT " +
                "GROUP_CONCAT(pokemon_forms.form_id || ',' || pokemon_forms.form_name || ',' || " +
                    "pokemon_forms.attack_probability || ',' || pokemon_forms.base_capture_rate || ',' || " +
                    "pokemon_forms.base_flee_rate || ',' || pokemon_forms.dodge_probability || ',' || " +
                    "pokemon_forms.min_pokemon_action_frequency || ',' || pokemon_forms.max_pokemon_action_frequency) " +
                "FROM pokemon_forms " +
                    "WHERE pokemon_table.pokemon_id = pokemon_forms.pokemon_uid " +
                    "AND pokemon_forms.form_id = :pokemonFormId) AS FORMS_LIST, " +
            "(SELECT " +
                "GROUP_CONCAT(pokemon_types.form_uid || ',' || pokemon_types.type_id || ',' || pokemon_types.type_name) " +
                "FROM pokemon_forms " +
                    "INNER JOIN pokemon_types " +
                        "ON pokemon_forms.form_id = pokemon_types.form_uid " +
                "WHERE pokemon_types.form_uid = pokemon_forms.form_id " +
                    "AND pokemon_table.pokemon_id = pokemon_forms.pokemon_uid " +
                    "AND pokemon_forms.form_id = :pokemonFormId) AS TYPES_LIST, " +
            "(SELECT " +
                "GROUP_CONCAT(pokemon_weather_boosts.type_uid || ',' || pokemon_weather_boosts.weather_name) " +
                "FROM pokemon_weather_boosts " +
                "INNER JOIN pokemon_types " +
                    "ON pokemon_types.type_id = pokemon_weather_boosts.type_uid " +
                "INNER JOIN pokemon_forms " +
                    "ON pokemon_forms.form_id = pokemon_types.form_uid " +
                "WHERE pokemon_types.type_id = pokemon_weather_boosts.type_uid " +
                    "AND pokemon_types.form_uid = pokemon_forms.form_id " +
                    "AND pokemon_table.pokemon_id = pokemon_forms.pokemon_uid " +
                    "AND pokemon_forms.form_id = :pokemonFormId) AS WEATHER_LIST " +
            "FROM pokemon_table WHERE pokemon_table.pokemon_id = :pokemonId")
    suspend fun getPokemon(pokemonId: Int, pokemonFormId: Int): PokemonFormsTypesWeatherBoosts

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

    @Query("UPDATE pokemon_table SET buddy_distances = :buddyDistances WHERE pokemon_id = :pokemonId")
    suspend fun updateBuddyDistances(buddyDistances: String, pokemonId: Int)

    @Query("UPDATE pokemon_table SET raid_exclusive = :raidExclusive, raid_level = :raidLevel WHERE pokemon_id = :pokemonId")
    suspend fun updateRaidExclusive(raidExclusive: Int, raidLevel: Int, pokemonId: Int)

    @Query("UPDATE pokemon_table SET nested_pokemon = :nestedPokemon WHERE pokemon_id = :pokemonId")
    suspend fun updateNestingPokemon(nestedPokemon: Int, pokemonId: Int)

    @Query("UPDATE pokemon_table " +
                "SET shiny_found_egg = :shinyFoundEgg, " +
                    "shiny_found_evolution = :shinyFoundEvolution, " +
                    "shiny_found_raid = :shinyFoundRaid, " +
                    "shiny_found_wild = :shinyFoundWild " +
                "WHERE pokemon_id = :pokemonId")
    suspend fun updateShinyPokemon(shinyFoundEgg: Int, shinyFoundEvolution: Int, shinyFoundRaid: Int, shinyFoundWild: Int, pokemonId: Int)

    @Query("UPDATE pokemon_table SET released_pokemon = :releasedPokemon WHERE pokemon_id = :pokemonId")
    suspend fun updateReleasedPokemon(releasedPokemon: Int, pokemonId: Int)

    @Query("UPDATE pokemon_table SET possible_ditto = :possibleDitto WHERE pokemon_id = :pokemonId")
    suspend fun updatePossibleDittoTypes(possibleDitto: Int, pokemonId: Int)

    @Query("UPDATE pokemon_forms " +
            "SET attack_probability = :attackProbability, " +
                "base_capture_rate = :baseCaptureRate, " +
                "base_flee_rate = :baseFleeRate, " +
                "dodge_probability = :dodgeProbability, " +
                "max_pokemon_action_frequency = :maxPokemonActionFrequency, " +
                "min_pokemon_action_frequency = :minPokemonActionFrequency " +
            "WHERE pokemon_uid = :pokemonId " +
                "AND form_name = :formName")
    suspend fun updatePokemonEncounterData(attackProbability: Double, baseCaptureRate: Double,
                                           baseFleeRate: Double, dodgeProbability: Double,
                                           maxPokemonActionFrequency: Double, minPokemonActionFrequency: Double,
                                           formName: String, pokemonId: Int)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAll()
}