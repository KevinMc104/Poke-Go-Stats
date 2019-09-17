package com.example.pokegostats.view.pokemon.detailed.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedListFragment
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedWeatherListFragment

class PokemonDetailedPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var pokemonId: Int = 0
    private var formId: Int = 0

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        var title = ""
        when(position) {
            0 -> title = "Stats"
            1 -> title = "Weather"
        }
        return title
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return PokemonDetailedListFragment.newInstance(pokemonId, formId)
            1 -> return PokemonDetailedWeatherListFragment.newInstance(pokemonId, formId)
        }
        return null!!
    }

    fun setPokemonId(pokemonId: Int) {
        this.pokemonId = pokemonId
    }

    fun setPokemonFormId(formId: Int) {
        this.formId = formId
    }
}