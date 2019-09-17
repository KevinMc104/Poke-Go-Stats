package com.example.pokegostats.injection

import com.example.pokegostats.view.home.PokemonFragmentModule
import com.example.pokegostats.view.home.PokemonListFragment
import com.example.pokegostats.view.home.PokemonMovesListFragment
import com.example.pokegostats.view.move.detailed.PokemonMoveDetailedFragment
import com.example.pokegostats.view.move.detailed.PokemonMoveDetailedFragmentModule
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedFragmentModule
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedListFragment
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedWeatherListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleBuilder {
    @ContributesAndroidInjector(modules = [PokemonFragmentModule::class])
    abstract fun pokemonListFragmentInject(): PokemonListFragment

    @ContributesAndroidInjector(modules = [PokemonFragmentModule::class])
    abstract fun pokemonMovesListFragmentInject(): PokemonMovesListFragment

    @ContributesAndroidInjector(modules = [PokemonDetailedFragmentModule::class])
    abstract fun pokemonDetailedListFragmentInject(): PokemonDetailedListFragment

    @ContributesAndroidInjector(modules = [PokemonDetailedFragmentModule::class])
    abstract fun pokemonDeatiledWeatherListFragmentInject(): PokemonDetailedWeatherListFragment

    @ContributesAndroidInjector(modules = [PokemonMoveDetailedFragmentModule::class])
    abstract fun pokemonMoveDetailedFragmentInject(): PokemonMoveDetailedFragment
}