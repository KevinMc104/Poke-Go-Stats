package com.example.pokegostats.model

import com.google.gson.annotations.SerializedName

/**
 * These models are based off the API response
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