package com.example.pokegostats.service

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pokegostats.R

class PokemonHelper {
    val POKEMON_ID: String = "Pokemon ID"
    val POKEMON_FORM_ID: String = "Pokemon Form ID"
    val POKEMON_FORM_NAME: String = "Pokemon Form Name"
    val POKEMON_MOVE_NAME: String = "Pokemon Move Name"
    val ERROR_MESSAGE: String = "Error Message"
    val RETRY_CALLS: String = "Retry Calls"

    companion object {
        val instance = PokemonHelper()
    }

    fun setPokemonTypeLook(context: Context, view: TextView, type: String) {
        if(type.isBlank()) {
            view.text = type
        } else {
            view.background = ContextCompat.getDrawable(context, when(type) {
                context.getString(R.string.normal) -> R.drawable.type_grass_drawable
                context.getString(R.string.fire) -> R.drawable.type_fire_drawable
                context.getString(R.string.water) -> R.drawable.type_water_drawable
                context.getString(R.string.electric) -> R.drawable.type_electric_drawable
                context.getString(R.string.grass) -> R.drawable.type_grass_drawable
                context.getString(R.string.ice) -> R.drawable.type_ice_drawable
                context.getString(R.string.fighting) -> R.drawable.type_fighting_drawable
                context.getString(R.string.poison) -> R.drawable.type_poison_drawable
                context.getString(R.string.ground) -> R.drawable.type_ground_drawable
                context.getString(R.string.flying) -> R.drawable.type_flying_drawable
                context.getString(R.string.psychic) -> R.drawable.type_psychic_drawable
                context.getString(R.string.bug) -> R.drawable.type_bug_drawable
                context.getString(R.string.rock) -> R.drawable.type_rock_drawable
                context.getString(R.string.ghost) -> R.drawable.type_ghost_drawable
                context.getString(R.string.dragon) -> R.drawable.type_dragon_drawable
                context.getString(R.string.dark) -> R.drawable.type_dark_drawable
                context.getString(R.string.steel) -> R.drawable.type_steel_drawable
                context.getString(R.string.fairy) -> R.drawable.type_fairy_drawable
                else -> Color.BLACK
            })
            // Spaces added to text for style purposes
            view.text = " $type "
        }
    }

    fun setPokemonImage(context: Context, view: ImageView, pokemonId: String, formName: String) {
        // Only have Alola Images currently
        val pokemon = if(formName == "Alola") {
            "$pokemonId,$formName"
        } else {
            pokemonId
        }
        view.setImageResource(when(pokemon) {
            context.getString(R.string.pokemon_1_bulbasaur) -> R.drawable.pokemon_1_bulbasaur
            context.getString(R.string.pokemon_2_ivysaur) -> R.drawable.pokemon_2_ivysaur
            context.getString(R.string.pokemon_3_venusaur) -> R.drawable.pokemon_3_venusaur
            context.getString(R.string.pokemon_4_charmander) -> R.drawable.pokemon_4_charmander
            context.getString(R.string.pokemon_5_charmeleon) -> R.drawable.pokemon_5_charmeleon
            context.getString(R.string.pokemon_6_charizard) -> R.drawable.pokemon_6_charizard
            context.getString(R.string.pokemon_7_squirtle) -> R.drawable.pokemon_7_squirtle
            context.getString(R.string.pokemon_8_wartortle) -> R.drawable.pokemon_8_wartortle
            context.getString(R.string.pokemon_9_blastoise) -> R.drawable.pokemon_9_blastoise
            context.getString(R.string.pokemon_10_caterpie) -> R.drawable.pokemon_10_caterpie
            context.getString(R.string.pokemon_11_metapod) -> R.drawable.pokemon_11_metapod
            context.getString(R.string.pokemon_12_butterfree) -> R.drawable.pokemon_12_butterfree
            context.getString(R.string.pokemon_13_weedle) -> R.drawable.pokemon_13_weedle
            context.getString(R.string.pokemon_14_kakuna) -> R.drawable.pokemon_14_kakuna
            context.getString(R.string.pokemon_15_beedrill) -> R.drawable.pokemon_15_beedrill
            context.getString(R.string.pokemon_16_pidgey) -> R.drawable.pokemon_16_pidgey
            context.getString(R.string.pokemon_17_pidgeotto) -> R.drawable.pokemon_17_pidgeotto
            context.getString(R.string.pokemon_18_pidgeot) -> R.drawable.pokemon_18_pidgeot
            context.getString(R.string.pokemon_19_rattata) -> R.drawable.pokemon_19_rattata
            context.getString(R.string.pokemon_19_rattata_alola) -> R.drawable.pokemon_19_rattata_alola
            context.getString(R.string.pokemon_20_raticate) -> R.drawable.pokemon_20_raticate
            context.getString(R.string.pokemon_20_raticate_alola) -> R.drawable.pokemon_20_raticate_alola
            context.getString(R.string.pokemon_21_spearow) -> R.drawable.pokemon_21_spearow
            context.getString(R.string.pokemon_22_fearow) -> R.drawable.pokemon_22_fearow
            context.getString(R.string.pokemon_23_ekans) -> R.drawable.pokemon_23_ekans
            context.getString(R.string.pokemon_24_arbok) -> R.drawable.pokemon_24_arbok
            context.getString(R.string.pokemon_25_pikachu) -> R.drawable.pokemon_25_pikachu
            context.getString(R.string.pokemon_26_raichu) -> R.drawable.pokemon_26_raichu
            context.getString(R.string.pokemon_26_raichu_alola) -> R.drawable.pokemon_26_raichu_alola
            context.getString(R.string.pokemon_27_sandshrew) -> R.drawable.pokemon_27_sandshrew
            context.getString(R.string.pokemon_27_sandshrew_alola) -> R.drawable.pokemon_27_sandshrew_alola
            context.getString(R.string.pokemon_28_sandslash) -> R.drawable.pokemon_28_sandslash
            context.getString(R.string.pokemon_28_sandslash_alola) -> R.drawable.pokemon_28_sandslash_alola
            context.getString(R.string.pokemon_29_nidoran) -> R.drawable.pokemon_29_nidoran
            context.getString(R.string.pokemon_30_nidorina) -> R.drawable.pokemon_30_nidorina
            context.getString(R.string.pokemon_31_nidoqueen) -> R.drawable.pokemon_31_nidoqueen
            context.getString(R.string.pokemon_32_nidoran) -> R.drawable.pokemon_32_nidoran
            context.getString(R.string.pokemon_33_nidorino) -> R.drawable.pokemon_33_nidorino
            context.getString(R.string.pokemon_34_nidoking) -> R.drawable.pokemon_34_nidoking

            context.getString(R.string.pokemon_172_pichu) -> R.drawable.pokemon_172_pichu
            else -> R.color.pokemonGoAppBackground
        })
    }
}
