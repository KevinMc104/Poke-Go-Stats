package com.example.pokegostats.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pokegostats.room.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table ORDER BY pokemon_id")
    fun getAll(): LiveData<List<PokemonEntity>>

//    @Query("SELECT * FROM user WHERE pokemon_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): PokemonEntity

    @Insert
    suspend fun insertAll(vararg pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAll()
}