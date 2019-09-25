package com.example.pokegostats.service

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pokegostats.R

class PokemonHelper {
    val POKEMON_ID: String = "Pokemon ID"
    val POKEMON_FORM_ID: String = "Pokemon Form ID"
    val POKEMON_MOVE_NAME: String = "Pokemon Move Name"

    companion object {
        val instance = PokemonHelper()
    }

    fun setPokemonTypeLook(context: Context, view: TextView, type: String) {
        if(type.isBlank()) {
            view.text = type
        } else {
            view.background = ContextCompat.getDrawable(context, when(type) {
                context.getString(R.string.normal) -> R.drawable.grass_type_drawable
                context.getString(R.string.fire) -> R.drawable.fire_type_drawable
                context.getString(R.string.water) -> R.drawable.water_type_drawable
                context.getString(R.string.electric) -> R.drawable.electric_type_drawable
                context.getString(R.string.grass) -> R.drawable.grass_type_drawable
                context.getString(R.string.ice) -> R.drawable.ice_type_drawable
                context.getString(R.string.fighting) -> R.drawable.fighting_type_drawable
                context.getString(R.string.poison) -> R.drawable.poison_type_drawable
                context.getString(R.string.ground) -> R.drawable.ground_type_drawable
                context.getString(R.string.flying) -> R.drawable.flying_type_drawable
                context.getString(R.string.psychic) -> R.drawable.psychic_type_drawable
                context.getString(R.string.bug) -> R.drawable.bug_type_drawable
                context.getString(R.string.rock) -> R.drawable.rock_type_drawable
                context.getString(R.string.ghost) -> R.drawable.ghost_type_drawable
                context.getString(R.string.dragon) -> R.drawable.dragon_type_drawable
                context.getString(R.string.dark) -> R.drawable.dark_type_drawable
                context.getString(R.string.steel) -> R.drawable.steel_type_drawable
                context.getString(R.string.fairy) -> R.drawable.fairy_type_drawable
                else -> Color.BLACK
            })
            view.text = type
        }
    }
}
