package com.example.pokegostats.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

/**
 * A Pokemon can have multiple Types and multiple different forms
 * The form the pokemon has can change which types it has
 * A Pokemon will always have at least one type
 *
 * Pokemon to many Forms(One to Many) and Form to Many types(One to Many)
 */

@Entity(tableName = "pokemon_table", indices = [Index("pokemon_id")])
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int,
    @ColumnInfo(name = "base_attack") val baseAttack: Int?,
    @ColumnInfo(name = "base_defense") val baseDefense: Int?,
    @ColumnInfo(name = "base_stamina") val baseStamina: Int?,
    @ColumnInfo(name = "max_cp") val maxCp: Int?,
    @ColumnInfo(name = "pokemon_name") val pokemonName: String?,
    @ColumnInfo(name = "candy_to_evolve") val candyToEvolve: String?
)

// One to Many relationship. One Pokemon can have multiple Forms
@Entity(
    tableName = "pokemon_forms",
    foreignKeys = [ForeignKey(
        entity = PokemonEntity::class,
        parentColumns = arrayOf("pokemon_id"),
        childColumns = arrayOf("pokemon_uid"),
        onDelete = CASCADE
    )], indices = [Index("form_id"), Index("pokemon_uid")]
)
data class PokemonFormEntity(
    @PrimaryKey
    @ColumnInfo(name = "form_id") val id: Long,
    @ColumnInfo(name = "pokemon_uid") val pokemonUid: Int,
    @ColumnInfo(name = "form_name") val formName: String
)

// One to Many relationship. One Form can have multiple Types
@Entity(
    tableName = "pokemon_types",
    foreignKeys = [ForeignKey(
        entity = PokemonFormEntity::class,
        parentColumns = arrayOf("form_id"),
        childColumns = arrayOf("form_uid"),
        onDelete = CASCADE
    )], indices = [Index("form_uid")]
)
data class PokemonTypeEntity(
    @PrimaryKey
    @ColumnInfo(name = "type_id") val id: Long,
    @ColumnInfo(name = "form_uid") val formUid: Long,
    @ColumnInfo(name = "type_name") val typeName: String
)

// One to Many relationship. One Type can have multiple Weather Boosts
@Entity(
    tableName = "pokemon_weather_boosts",
    foreignKeys = [ForeignKey(
        entity = PokemonTypeEntity::class,
        parentColumns = arrayOf("type_id"),
        childColumns = arrayOf("type_uid"),
        onDelete = CASCADE
    )], indices = [Index("type_uid")]
)
data class PokemonWeatherBoostsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weather_id") val id: Long?,
    @ColumnInfo(name = "type_uid") val typeUid: Long,
    @ColumnInfo(name = "weather_name") val weatherName: String
)

// Relationship class to get each Pokemon's Forms/Types
// TODO: Remove this relationship and just use the PokemonFormsTypesWeatherBoosts
class PokemonAndFormsAndTypes {
    @Embedded
    var pokemon: PokemonEntity? = null

    @Embedded
    var pokemonForm: PokemonFormEntity? = null

    @Embedded
    var pokemonType: PokemonTypeEntity? = null

    @Relation(parentColumn = "form_id", entityColumn = "form_uid", entity = PokemonTypeEntity::class)
    var pokemonTypes: List<PokemonTypeEntity>? = null

    @Relation(parentColumn = "type_id", entityColumn = "type_uid", entity = PokemonWeatherBoostsEntity::class)
    var pokemonWeatherBoosts: List<PokemonWeatherBoostsEntity>? = null
}

/**
 * Pokemon Entity Fields
 * var names MUST match SQL Statment column names found in the SQL statement in
 * PokemonDao.getAllPokemonFormsTypesWeatherBoosts()
 *
 * Initial format of List fields before Map Transformation in PokemonGoStatsRepository
 * FORMS_LIST = formId, formName
 * TYPES_LIST = formId, typeId, typeName
 * WEATHER_LIST = typeId, weatherName
 *
 * Flattened Lists Formats
 * FORMS_LIST = formId, formName
 * TYPES_LIST = typeId, typeName
 * WEATHER_LIST = weatherName
 */
class PokemonFormsTypesWeatherBoosts {
    var pokemon_id: Int = 0
    var base_attack: Int? = 0
    var base_defense: Int? = 0
    var base_stamina: Int? = 0
    var max_cp: Int? = 0
    var pokemon_name: String? = ""
    var candy_to_evolve: String? = ""
    @TypeConverters(Converters::class)
    var FORMS_LIST: ArrayList<String>? = ArrayList()
    @TypeConverters(Converters::class)
    var TYPES_LIST: ArrayList<String>? = ArrayList()
    @TypeConverters(Converters::class)
    var WEATHER_LIST: ArrayList<String>? = ArrayList()
}

@TypeConverters
class Converters {
    @TypeConverter
    fun fromGroupConcat(groupConcat: String?): ArrayList<String> {
        return if(!groupConcat.isNullOrBlank()) {
            ArrayList(groupConcat.split(",").map{it.trim()})
        } else {
            ArrayList()
        }
    }
}
