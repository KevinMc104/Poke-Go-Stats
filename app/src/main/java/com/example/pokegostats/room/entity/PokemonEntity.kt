package com.example.pokegostats.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid: Int?,
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int,
    @ColumnInfo(name = "base_attack") val baseAttack: Int,
    @ColumnInfo(name = "base_defense") val BaseDefense: Int,
    @ColumnInfo(name = "base_stamina") val BaseStamina: Int,
    @ColumnInfo(name = "pokemon_name") val pokemonName: String?
)