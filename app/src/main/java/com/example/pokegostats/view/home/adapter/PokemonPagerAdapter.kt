package com.example.pokegostats.view.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pokegostats.view.home.PokemonListFragment
import com.example.pokegostats.view.home.PokemonMovesListFragment

class PokemonPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var retryCalls = false

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        var title = ""
        when(position) {
            0 -> title = "Pokemon"
            1 -> title = "Moves"
        }
        return title
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return PokemonListFragment.newInstance(retryCalls)
            1 -> return PokemonMovesListFragment.newInstance()
        }
        return null!!
    }

    fun setRetryCalls(retryCalls: Boolean) {
        this.retryCalls = retryCalls
    }
}