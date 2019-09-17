package com.example.pokegostats.model

import com.google.gson.annotations.SerializedName

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/weather_boosts.json
 *
 * During different weather certain types will be boosted.
 * When they are boosted Pokemon of the boosted types will be
 * found at a higher level, and moves of that type will be more powerful.
 * This API lists what each weather type boosts. Returns a JSON dict
 * where each key is the weather type and the value is an array of boosted types.
 */
data class RapidPokemonGoWeatherBoosts(
    @SerializedName("Clear")
    val Clear: List<String>,
    @SerializedName("Cloudy")
    val Cloudy: List<String>,
    @SerializedName("Fog")
    val Fog: List<String>,
    @SerializedName("Partly cloudy")
    val PartlyCloudy: List<String>,
    @SerializedName("Rain")
    val Rain: List<String>,
    @SerializedName("Snow")
    val Snow: List<String>,
    @SerializedName("Sunny")
    val Sunny: List<String>,
    @SerializedName("Wind")
    val Wind: List<String>
)