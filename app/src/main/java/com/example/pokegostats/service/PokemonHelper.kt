package com.example.pokegostats.service

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.example.pokegostats.R

class PokemonHelper {
    val POKEMON_ID: String = "Pokemon ID"
    val POKEMON_FORM_ID: String = "Pokemon Form ID"
    val POKEMON_MOVE_NAME: String = "Pokemon Move Name"

    companion object {
        val instance = PokemonHelper()
    }

    fun setPokemonTypeLook(context: Context, view: TextView, type: String) {
        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.rounded_type)!!
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable).mutate()

        view.setTextColor(Color.WHITE)
        view.setShadowLayer(3.0f,3.0f,3.0f, Color.BLACK)
        DrawableCompat.setTint(wrappedDrawable, setColorByType(type))
        view.background = wrappedDrawable
        view.text = type
    }

    private fun setColorByType(type: String): Int {
        when {
            type.equals("Normal") -> return Color.parseColor("#AAAA99")
            type.equals("Fire") -> return Color.parseColor("#FF4422")
            type.equals("Water") -> return Color.parseColor("#3399FF")
            type.equals("Electric") -> return Color.parseColor("#FFCC33")
            type.equals("Grass") -> return Color.parseColor("#77CC55")
            type.equals("Ice") -> return Color.parseColor("#66CCFF")
            type.equals("Fighting") -> return Color.parseColor("#BB5544")
            type.equals("Poison") -> return Color.parseColor("#AA5599")
            type.equals("Ground") -> return Color.parseColor("#DDBB55")
            type.equals("Flying") -> return Color.parseColor("#8899FF")
            type.equals("Psychic") -> return Color.parseColor("#FF5599")
            type.equals("Bug") -> return Color.parseColor("#AABB22")
            type.equals("Rock") -> return Color.parseColor("#BBAA66")
            type.equals("Ghost") -> return Color.parseColor("#6666BB")
            type.equals("Dragon") -> return Color.parseColor("#7766EE")
            type.equals("Dark") -> return Color.parseColor("#775544")
            type.equals("Steel") -> return Color.parseColor("#AAAABB")
            type.equals("Fairy") -> return Color.parseColor("#EE99EE")
        }
        return Color.BLACK
    }
}
