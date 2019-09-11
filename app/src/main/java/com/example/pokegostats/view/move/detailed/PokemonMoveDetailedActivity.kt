package com.example.pokegostats.view.move.detailed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonHelper
import kotlinx.android.synthetic.main.pokemon_moves_detailed_activity.*

class PokemonMoveDetailedActivity : AppCompatActivity() {

    private val helper: PokemonHelper = PokemonHelper.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_moves_detailed_activity)

        val moveName = intent.extras!!.getString(helper.POKEMON_MOVE_NAME).toString()
        tv_big_move_name.text = moveName
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, PokemonMoveDetailedFragment.newInstance(moveName))
                .commitNow()
        }
    }
}