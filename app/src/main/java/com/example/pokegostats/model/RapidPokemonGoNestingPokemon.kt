package com.example.pokegostats.model

import com.google.gson.annotations.SerializedName

/**
 * These models are based off the JSON API response
 * GET - https://pokemon-go1.p.rapidapi.com/pokemon_buddy_distances.json
 *
 * Only specific Pokemon will nest and this API lets you get the name and
 * ID of all Pokemon known to currently nest.
 * Returns a JSON dict with the keys being the Pokemon ID,
 * the values are an array containing the pokemon name and ID.
 *
 * Whoever designed the JSON response I want you to somehow survive a terrible car crash
 * and somehow not survive a small fender-bender on the way back from the hospital
 */
data class RapidPokemonGoNestingPokemon(
    @SerializedName("1")
    val One: RapidNestingPokemon,
    @SerializedName("4")
    val Four: RapidNestingPokemon,
    @SerializedName("7")
    val Seven: RapidNestingPokemon,
    @SerializedName("25")
    val TwentyFive: RapidNestingPokemon,
    @SerializedName("35")
    val ThirtyFive: RapidNestingPokemon,
    @SerializedName("37")
    val ThirtySeven: RapidNestingPokemon,
    @SerializedName("43")
    val FortyThree: RapidNestingPokemon,
    @SerializedName("54")
    val FiftyFour: RapidNestingPokemon,
    @SerializedName("58")
    val FiftyEight: RapidNestingPokemon,
    @SerializedName("60")
    val Sixty: RapidNestingPokemon,
    @SerializedName("63")
    val SixtyThree: RapidNestingPokemon,
    @SerializedName("66")
    val SixtySix: RapidNestingPokemon,
    @SerializedName("72")
    val SeventyTwo: RapidNestingPokemon,
    @SerializedName("77")
    val SeventySeven: RapidNestingPokemon,
    @SerializedName("81")
    val EightyOne: RapidNestingPokemon,
    @SerializedName("84")
    val EightyFour: RapidNestingPokemon,
    @SerializedName("86")
    val EightySix: RapidNestingPokemon,
    @SerializedName("90")
    val Ninety: RapidNestingPokemon,
    @SerializedName("92")
    val NinetyTwo: RapidNestingPokemon,
    @SerializedName("95")
    val NinetyFive: RapidNestingPokemon,
    @SerializedName("100")
    val OneHundred: RapidNestingPokemon,
    @SerializedName("102")
    val OneHundredTwo: RapidNestingPokemon,
    @SerializedName("104")
    val OneHundredFour: RapidNestingPokemon,
    @SerializedName("111")
    val OneHundredEleven: RapidNestingPokemon,
    @SerializedName("116")
    val OneHundredSixteen: RapidNestingPokemon,
    @SerializedName("123")
    val OneHundredTwentyThree: RapidNestingPokemon,
    @SerializedName("124")
    val OneHundredTwentyFour: RapidNestingPokemon,
    @SerializedName("125")
    val OneHundredTwentyFive: RapidNestingPokemon,
    @SerializedName("126")
    val OneHundredTwentySix:RapidNestingPokemon,
    @SerializedName("127")
    val OneHundredTwentySeven: RapidNestingPokemon,
    @SerializedName("129")
    val OneHundredTwentyNine: RapidNestingPokemon,
    @SerializedName("133")
    val OneHundredThirtyThree: RapidNestingPokemon,
    @SerializedName("138")
    val OneHundredThirtyEight: RapidNestingPokemon,
    @SerializedName("140")
    val OneHundredForty: RapidNestingPokemon,
    @SerializedName("152")
    val OneHundredFiftyTwo: RapidNestingPokemon,
    @SerializedName("155")
    val OneHundredFiftyFive: RapidNestingPokemon,
    @SerializedName("158")
    val OneHundredFiftyEight: RapidNestingPokemon,
    @SerializedName("170")
    val OneHundredSeventy: RapidNestingPokemon,
    @SerializedName("185")
    val OneHundredEightyFive: RapidNestingPokemon,
    @SerializedName("190")
    val OneHundredNinety: RapidNestingPokemon,
    @SerializedName("193")
    val OneHundredNinetyThree: RapidNestingPokemon,
    @SerializedName("200")
    val TwoHundred: RapidNestingPokemon,
    @SerializedName("202")
    val TwoHundredTwo: RapidNestingPokemon,
    @SerializedName("203")
    val TwoHundredThree: RapidNestingPokemon,
    @SerializedName("206")
    val TwoHundredSix: RapidNestingPokemon,
    @SerializedName("209")
    val TwoHundredNine: RapidNestingPokemon,
    @SerializedName("211")
    val TwoHundredEleven: RapidNestingPokemon,
    @SerializedName("213")
    val TwoHundredThirteen: RapidNestingPokemon,
    @SerializedName("215")
    val TwoHundredFifteen: RapidNestingPokemon,
    @SerializedName("216")
    val TwoHundredSixteen: RapidNestingPokemon,
    @SerializedName("220")
    val TwoHundredTwenty: RapidNestingPokemon,
    @SerializedName("226")
    val TwoHundredTwentySix: RapidNestingPokemon,
    @SerializedName("231")
    val TwoHundredThirtyOne: RapidNestingPokemon,
    @SerializedName("234")
    val TwoHundredThirtyFour: RapidNestingPokemon,
    @SerializedName("252")
    val TwoHundredFiftyTwo: RapidNestingPokemon,
    @SerializedName("255")
    val TwoHundredFiftyFive: RapidNestingPokemon,
    @SerializedName("258")
    val TwoHundredFiftyEight: RapidNestingPokemon,
    @SerializedName("261")
    val TwoHundredSixtyOne: RapidNestingPokemon,
    @SerializedName("273")
    val TwoHundredSeventyThree: RapidNestingPokemon,
    @SerializedName("278")
    val TwoHundredSeventyEight: RapidNestingPokemon,
    @SerializedName("283")
    val TwoHundredEightyThree: RapidNestingPokemon,
    @SerializedName("285")
    val TwoHundredEightyFive: RapidNestingPokemon,
    @SerializedName("296")
    val TwoHundredNinetySix: RapidNestingPokemon,
    @SerializedName("299")
    val TwoHundredNinetyNine: RapidNestingPokemon,
    @SerializedName("300")
    val ThreeHundred: RapidNestingPokemon,
    @SerializedName("302")
    val ThreeHundredTwo: RapidNestingPokemon,
    @SerializedName("307")
    val ThreeHundredSeven: RapidNestingPokemon,
    @SerializedName("309")
    val ThreeHundredNine: RapidNestingPokemon,
    @SerializedName("311")
    val ThreeHundredEleven: RapidNestingPokemon,
    @SerializedName("312")
    val ThreeHundredTwelve: RapidNestingPokemon,
    @SerializedName("318")
    val ThreeHundredEighteen: RapidNestingPokemon,
    @SerializedName("320")
    val ThreeHundredTwenty: RapidNestingPokemon,
    @SerializedName("322")
    val ThreeHundredTwentyTwo: RapidNestingPokemon,
    @SerializedName("325")
    val ThreeHundredTwentyFive: RapidNestingPokemon,
    @SerializedName("333")
    val ThreeHundredThirtyThree: RapidNestingPokemon,
    @SerializedName("341")
    val ThreeHundredFortyOne: RapidNestingPokemon,
    @SerializedName("343")
    val ThreeHundredFortyThree: RapidNestingPokemon,
    @SerializedName("345")
    val ThreeHundredFortyFive: RapidNestingPokemon,
    @SerializedName("347")
    val ThreeHundredFortySeven: RapidNestingPokemon,
    @SerializedName("353")
    val ThreeHundredFiftyThree: RapidNestingPokemon,
    @SerializedName("355")
    val ThreeHundredFiftyFive: RapidNestingPokemon,
    @SerializedName("370")
    val ThreeHundredSeventy: RapidNestingPokemon,
    @SerializedName("387")
    val ThreeHundredEightySeven: RapidNestingPokemon,
    @SerializedName("390")
    val ThreeHundredNinety: RapidNestingPokemon,
    @SerializedName("393")
    val ThreeHundredNinetyThree: RapidNestingPokemon,
    @SerializedName("399")
    val ThreeHundredNinetyNine: RapidNestingPokemon
)

data class RapidNestingPokemon(
    val Id: String
)