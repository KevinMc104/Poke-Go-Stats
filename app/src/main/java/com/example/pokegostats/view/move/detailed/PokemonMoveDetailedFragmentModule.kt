package com.example.pokegostats.view.move.detailed

import dagger.Binds
import dagger.Module

@Module
abstract class PokemonMoveDetailedFragmentModule {
    @Binds
    abstract fun providePokemonMoveDetailedFragment(pokemonMoveDetailedFragment: PokemonMoveDetailedFragment): PokemonMoveDetailedFragment
}