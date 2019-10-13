package com.analytics.pokegostats.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.analytics.pokegostats.R
import com.analytics.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.analytics.pokegostats.service.PokemonHelper
import kotlinx.android.synthetic.main.pokemon_stats_row.view.*

class PokemonStatsRowView @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val helper: PokemonHelper = PokemonHelper.instance

    init {
        if (attrs == null) {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        inflate(context, R.layout.pokemon_stats_row, this)
    }

    fun setup(pokemon: PokemonFormsTypesWeatherBoosts) {
        var formName = pokemon.FORMS_LIST!![1]
        // Don't add form name if it's Default
        if(formName != "Default") {
            formName = "(${formName})"
        } else {
            formName = ""
        }

        val pokemonTypes = pokemon.TYPES_LIST!!
        if (pokemonTypes.size == 2) {
            helper.setPokemonTypeLook(context, tv_menu_type1, pokemonTypes[1])
            helper.setPokemonTypeLook(context, tv_menu_type2, "")
        } else if (pokemonTypes.size == 4) {
            helper.setPokemonTypeLook(context, tv_menu_type1, pokemonTypes[1])
            helper.setPokemonTypeLook(context, tv_menu_type2, pokemonTypes[3])
        }

        tv_menu_pokemon_name.text = pokemon.pokemon_name
        tv_menu_pokemon_form_name.text = formName
        tv_menu_max_cp.text = pokemon.max_cp.toString()
    }
}