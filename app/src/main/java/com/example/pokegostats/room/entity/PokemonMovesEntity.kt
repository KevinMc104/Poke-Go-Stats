package com.example.pokegostats.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_moves_table", indices = [Index("move_name")])
data class PokemonMovesEntity(
    @PrimaryKey
    @ColumnInfo(name = "move_name") val name: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "critical_chance") val criticalChance: Double,
    @ColumnInfo(name = "energy_delta") val energyDelta: Int,
    @ColumnInfo(name = "power") val power: Int,
    @ColumnInfo(name = "stamina_loss_scaler") val staminaLossScaler: String,
    @ColumnInfo(name = "type_name") val typeName: String
)
