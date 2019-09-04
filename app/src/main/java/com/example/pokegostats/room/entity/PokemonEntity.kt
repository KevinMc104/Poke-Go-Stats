package com.example.pokegostats.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int,
    @ColumnInfo(name = "base_attack") val baseAttack: String?,
    @ColumnInfo(name = "pokemon_name") val pokemonName: String?
)