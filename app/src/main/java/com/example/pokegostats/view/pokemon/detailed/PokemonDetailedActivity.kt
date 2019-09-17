package com.example.pokegostats.view.pokemon.detailed

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.pokemon.detailed.adapter.PokemonDetailedPagerAdapter
import com.google.android.material.tabs.TabLayout

class PokemonDetailedActivity : AppCompatActivity() {

    private lateinit var adapter: PokemonDetailedPagerAdapter
    private val helper: PokemonHelper = PokemonHelper.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_detailed_activity)

        val vpPager = findViewById<ViewPager>(R.id.view_pager_detailed)
        adapter = PokemonDetailedPagerAdapter(supportFragmentManager)
        val pokemonId = intent.extras!!.getString(helper.POKEMON_ID)!!.toInt()
        val formId = intent.extras!!.getString(helper.POKEMON_FORM_ID)!!.toInt()
        adapter.setPokemonFormId(formId)
        adapter.setPokemonId(pokemonId)
        vpPager.adapter = adapter

        val tabs = findViewById<View>(R.id.tab_layout_detailed) as TabLayout
        tabs.setupWithViewPager(vpPager)
    }
}