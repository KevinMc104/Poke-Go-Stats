package com.example.pokegostats.service

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pokegostats.R

class PokemonHelper {
    val POKEMON_ID: String = "Pokemon ID"
    val POKEMON_NAME: String = "Pokemon NAME"
    val POKEMON_FORM_ID: String = "Pokemon Form ID"
    val POKEMON_FORM_NAME: String = "Pokemon Form Name"
    val POKEMON_TYPE1: String = "Pokemon Type 1"
    val POKEMON_TYPE2: String = "Pokemon Type 2"
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
}
