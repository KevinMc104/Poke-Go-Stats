package com.example.pokegostats.view.home

import android.graphics.Color

class PokemonHelper {

    companion object {
        val instance = PokemonHelper()
    }

    fun setColorByType(type: String): Int {
        when {
            type.equals("Normal") -> return Color.parseColor("#aaaa99")
            type.equals("Fire") -> return Color.parseColor("#ff4422")
            type.equals("Water") -> return Color.parseColor("#3399ff")
            type.equals("Electric") -> return Color.parseColor("#ffcc33")
            type.equals("Grass") -> return Color.parseColor("#77cc55")
            type.equals("Ice") -> return Color.parseColor("#66ccff")
            type.equals("Fighting") -> return Color.parseColor("#bb5544")
            type.equals("Poison") -> return Color.parseColor("#aa5599")
            type.equals("Ground") -> return Color.parseColor("#ddbb55")
            type.equals("Flying") -> return Color.parseColor("#8899ff")
            type.equals("Psychic") -> return Color.parseColor("#ff5599")
            type.equals("Bug") -> return Color.parseColor("#aabb22")
            type.equals("Rock") -> return Color.parseColor("#bbaa66")
            type.equals("Ghost") -> return Color.parseColor("#6666bb")
            type.equals("Dragon") -> return Color.parseColor("#7766ee")
            type.equals("Dark") -> return Color.parseColor("#775544")
            type.equals("Steel") -> return Color.parseColor("#aaaabb")
            type.equals("Fairy") -> return Color.parseColor("#ee99ee")
        }
        return Color.BLACK
    }
}
