package com.example.pokegostats.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokegostats.R

class PokemonMovesRowHeaderView @JvmOverloads constructor(context: Context,
                                                          attrs: AttributeSet? = null,
                                                          defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        if (attrs == null) {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        inflate(context, R.layout.pokemon_moves_row_header, this)
    }
}