package com.example.pokegostats.model

import com.google.gson.annotations.SerializedName

/**
 * These models are based off the API response
 * https://pokemon-go1.p.rapidapi.com/pokemon_candy_to_evolve.json
 */
data class RapidPokemonGoCandyEvolve(
    @SerializedName("100")
    val OneHundred: List<RapidCandyEvolve>,
    @SerializedName("12")
    val Twelve: List<RapidCandyEvolve>,
    @SerializedName("22")
    val TwentyTwo: List<RapidCandyEvolve>,
    @SerializedName("25")
    val TwentyFive: List<RapidCandyEvolve>,
    @SerializedName("360")
    val ThreeHundredSixty: List<RapidCandyEvolve>,
    @SerializedName("400")
    val FourHundred: List<RapidCandyEvolve>,
    @SerializedName("45")
    val FortyFive: List<RapidCandyEvolve>,
    @SerializedName("50")
    val Fifty: List<RapidCandyEvolve>,
    @SerializedName("90")
    val Ninety: List<RapidCandyEvolve>
)

data class RapidCandyEvolve(
    val CandyRequired: String,
    val PokemonId: String
)