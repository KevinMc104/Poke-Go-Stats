package com.example.pokegostats.view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonHelper
import kotlinx.android.synthetic.main.pokemon_detailed_weather_row.view.*
import java.util.*

class PokemonDetailedWeatherRowView @JvmOverloads constructor(context: Context,
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
        inflate(context, R.layout.pokemon_detailed_weather_row, this)
    }

    fun setup(statName: String) {
        tv_weather_name.text = statName
        var identifier = "weather_" + statName.toLowerCase(Locale.getDefault())
        // Files can't have any special characters besides "_"
        // Remove any non alphanumeric characters from the search
        val re = Regex("[^A-Za-z0-9_]")
        identifier = re.replace(identifier, "")
        // Explanation for ID retrieval found here: http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
        // getIdentifier is the slower way to do this
        try {
            val res = R.drawable::class.java
            val drawableId = res.getField(identifier).getInt(null)
            weather_image.setImageResource(drawableId)
        } catch (e: Exception) {
            Log.i("DrawableWarning", "Image for $identifier doesn't exist")
        }
    }
}