package com.analytics.pokegostats.view.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.analytics.pokegostats.R
import com.analytics.pokegostats.service.PokemonHelper
import kotlinx.android.synthetic.main.pokemon_detailed_row.view.*

class PokemonDetailedRowView @JvmOverloads constructor(context: Context,
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
        inflate(context, R.layout.pokemon_detailed_row, this)
    }

    fun setup(statName: String, statValue: String, statValue2: String?, isType: Boolean) {
        tv_menu_stat_name.text = statName
        if(isType) {
            if(statValue2.isNullOrBlank()) {
                tv_menu_stat_value.setTextColor(Color.WHITE)
                tv_menu_stat_value.setShadowLayer(3.0f, 3.0f, 3.0f, Color.BLACK)
                helper.setPokemonTypeLook(context, tv_menu_stat_value, statValue)
            } else {
                tv_menu_stat_value.setTextColor(Color.WHITE)
                tv_menu_stat_value.setShadowLayer(3.0f, 3.0f, 3.0f, Color.BLACK)
                tv_menu_stat_value2.setTextColor(Color.WHITE)
                tv_menu_stat_value2.setShadowLayer(3.0f, 3.0f, 3.0f, Color.BLACK)
                helper.setPokemonTypeLook(context, tv_menu_stat_value, statValue)
                helper.setPokemonTypeLook(context, tv_menu_stat_value2, statValue2)
            }
        } else {
            tv_menu_stat_value.text = statValue
            if(!statValue2.isNullOrBlank()) {
                tv_menu_stat_value2.text = statValue2
            }
        }
    }
}