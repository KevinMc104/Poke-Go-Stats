package com.example.pokegostats.view.move.detailed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonHelper
import kotlinx.android.synthetic.main.pokemon_moves_detailed_activity.*

class PokemonMoveDetailedActivity : AppCompatActivity() {

    private val helper: PokemonHelper = PokemonHelper.instance
    private lateinit var viewModel: PokemonMoveDetailedActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_moves_detailed_activity)

        // Creates the View Model
        val factory = PokemonMoveDetailedActivityViewModel.Companion.Factory(this.application)
        viewModel = ViewModelProvider(this, factory).get(PokemonMoveDetailedActivityViewModel::class.java)

        val moveName = intent.extras!!.getString(helper.POKEMON_MOVE_NAME).toString()
        tv_big_move_name.text = moveName
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, PokemonMoveDetailedFragment.newInstance(moveName))
                .commitNow()
        }
    }
}