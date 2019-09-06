package com.example.pokegostats.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "pokemon_table", indices = arrayOf(Index("pokemon_id")))
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int,
    @ColumnInfo(name = "base_attack") val baseAttack: Int?,
    @ColumnInfo(name = "base_defense") val BaseDefense: Int?,
    @ColumnInfo(name = "base_stamina") val BaseStamina: Int?,
    @ColumnInfo(name = "pokemon_name") val pokemonName: String?
)

// One to Many relationship. One Pokemon can have multiple types
@Entity(
    tableName = "pokemon_types",
    foreignKeys = [ForeignKey(
        entity = PokemonEntity::class,
        parentColumns = arrayOf("pokemon_id"),
        childColumns = arrayOf("pokemon_uid"),
        onDelete = CASCADE
    )], indices = arrayOf(Index("pokemon_uid"))
)
data class PokemonTypeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "pokemon_uid") val pokemonUid: Int,
    @ColumnInfo(name = "type") val type: String?
)

// Relationship class to get each Pokemon's Types
class PokemonAndPokemonTypes {
    @Embedded
    var pokemon: PokemonEntity? = null

    @Relation(parentColumn = "pokemon_id", entityColumn = "pokemon_uid", entity = PokemonTypeEntity::class)
    var pokemonsTypes: List<PokemonTypeEntity>? = null
}

