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
                "Normal" -> R.drawable.grass_type_drawable
                "Fire" -> R.drawable.fire_type_drawable
                "Water" -> R.drawable.water_type_drawable
                "Electric" -> R.drawable.electric_type_drawable
                "Grass" -> R.drawable.grass_type_drawable
                "Ice" -> R.drawable.ice_type_drawable
                "Fighting" -> R.drawable.fighting_type_drawable
                "Poison" -> R.drawable.poison_type_drawable
                "Ground" -> R.drawable.ground_type_drawable
                "Flying" -> R.drawable.flying_type_drawable
                "Psychic" -> R.drawable.psychic_type_drawable
                "Bug" -> R.drawable.bug_type_drawable
                "Rock" -> R.drawable.rock_type_drawable
                "Ghost" -> R.drawable.ghost_type_drawable
                "Dragon" -> R.drawable.dragon_type_drawable
                "Dark" -> R.drawable.dark_type_drawable
                "Steel" -> R.drawable.steel_type_drawable
                "Fairy" -> R.drawable.fairy_type_drawable
                else -> Color.BLACK
            })
            view.text = type
        }
    }
}
