package com.example.pokegostats.view.pokemon.detailed

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.pokemon.detailed.adapter.PokemonDetailedPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.pokemon_detailed_activity.*

class PokemonDetailedActivity : AppCompatActivity() {

    private lateinit var adapter: PokemonDetailedPagerAdapter
    private val helper: PokemonHelper = PokemonHelper.instance
    private lateinit var viewModel: PokemonDetailedActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_detailed_activity)

        // Creates the View Model
        val factory = PokemonDetailedActivityViewModel.Companion.Factory(this.application)
        viewModel = ViewModelProvider(this, factory).get(PokemonDetailedActivityViewModel::class.java)

        val vpPager = findViewById<ViewPager>(R.id.view_pager_detailed)
        adapter = PokemonDetailedPagerAdapter(supportFragmentManager)
        val pokemonId = intent.extras!!.getString(helper.POKEMON_ID)!!
        val formId = intent.extras!!.getString(helper.POKEMON_FORM_ID)!!.toInt()
        val formName = intent.extras!!.getString(helper.POKEMON_FORM_NAME)!!
        helper.setPokemonImage(this, pokemon_image, pokemonId, formName)
        adapter.setPokemonFormId(formId)
        adapter.setPokemonId(pokemonId.toInt())
        vpPager.adapter = adapter

        val tabs = findViewById<View>(R.id.tab_layout_detailed) as TabLayout
        tabs.setupWithViewPager(vpPager)
    }
}