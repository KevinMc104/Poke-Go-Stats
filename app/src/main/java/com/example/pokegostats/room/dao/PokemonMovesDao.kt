package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pokegostats.room.entity.PokemonMovesEntity

@Dao
interface PokemonMovesDao {
    @Query("SELECT * FROM pokemon_moves_table")
    fun getAllMoves(): LiveData<List<PokemonMovesEntity>>

    @Query("SELECT * FROM pokemon_moves_table WHERE move_name = :moveName")
    suspend fun getMove(moveName: String): PokemonMovesEntity

    @Update
    suspend fun update(vararg pokemon: PokemonMovesEntity)

    @Insert
    suspend fun insertAll(vararg pokemon: PokemonMovesEntity)

    @Delete
    suspend fun delete(pokemon: PokemonMovesEntity)

    @Query("DELETE FROM pokemon_moves_table")
    suspend fun deleteAll()
}