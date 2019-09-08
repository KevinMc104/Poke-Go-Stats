package com.example.pokegostats.injection

import com.example.pokegostats.view.home.PokemonListFragment
import com.example.pokegostats.view.home.PokemonFragmentModule
import com.example.pokegostats.view.home.PokemonMovesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleBuilder {
    @ContributesAndroidInjector(modules = [PokemonFragmentModule::class])
    abstract fun pokemonListFragmentInject(): PokemonListFragment

    @ContributesAndroidInjector(modules = [PokemonFragmentModule::class])
    abstract fun pokemonMovesListFragmentInject(): PokemonMovesListFragment
}