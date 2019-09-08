package com.example.pokegostats.view.home

import dagger.Binds
import dagger.Module

@Module
abstract class PokemonFragmentModule {
    @Binds
    abstract fun providePokemonListFragment(pokemonListFragment: PokemonListFragment): PokemonListFragment

    @Binds
    abstract fun providePokemonMovesListFragment(pokemonMovesListFragment: PokemonMovesListFragment): PokemonMovesListFragment
}