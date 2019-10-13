package com.analytics.pokegostats.view.pokemon.detailed

import dagger.Binds
import dagger.Module

@Module
abstract class PokemonDetailedFragmentModule {
    @Binds
    abstract fun providePokemonDetailedListFragment(pokemonDetailedListFragment: PokemonDetailedListFragment): PokemonDetailedListFragment

    @Binds
    abstract fun providePokemonDetailedWeatherListFragment(pokemonDetailedWeatherListFragment: PokemonDetailedWeatherListFragment): PokemonDetailedWeatherListFragment
}