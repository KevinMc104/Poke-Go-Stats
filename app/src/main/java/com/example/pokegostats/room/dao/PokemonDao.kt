package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonFormEntity
import com.example.pokegostats.room.entity.PokemonTypeEntity
import com.example.pokegostats.room.entity.PokemonWeatherBoostsEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table ORDER BY pokemon_id")
    fun getAllPokemon(): LiveData<List<PokemonEntity>>

    @Update
    suspend fun update(vararg pokemon: PokemonEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllPokemon(vararg pokemon: PokemonEntity)

    @Insert
    suspend fun insertAllForms(vararg forms: PokemonFormEntity): List<Long>

    @Insert
    suspend fun insertAllTypes(vararg types: PokemonTypeEntity): List<Long>

    @Insert
    suspend fun insertAllWeatherBoosts(vararg types: PokemonWeatherBoostsEntity): List<Long>

    @Query("UPDATE pokemon_table SET max_cp = :maxCp WHERE pokemon_id = :pokemonId")
    suspend fun updateMaxCp(maxCp: Int, pokemonId: Int)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAll()
}