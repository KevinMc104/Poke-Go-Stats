package com.analytics.pokegostats.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.analytics.pokegostats.R
import com.analytics.pokegostats.room.entity.PokemonMovesEntity
import com.analytics.pokegostats.service.PokemonHelper
import kotlinx.android.synthetic.main.pokemon_moves_row.view.*

class PokemonMovesRowView @JvmOverloads constructor(context: Context,
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
        inflate(context, R.layout.pokemon_moves_row, this)
    }

    fun setup(move: PokemonMovesEntity) {
        tv_menu_move_name.text = move.name
        tv_menu_power.text = move.power.toString()
        helper.setPokemonTypeLook(context, tv_menu_type, move.typeName)
    }
}