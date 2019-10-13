package com.analytics.pokegostats.injection

import com.analytics.pokegostats.view.home.PokemonFragmentModule
import com.analytics.pokegostats.view.home.PokemonListFragment
import com.analytics.pokegostats.view.home.PokemonMovesListFragment
import com.analytics.pokegostats.view.move.detailed.PokemonMoveDetailedFragment
import com.analytics.pokegostats.view.move.detailed.PokemonMoveDetailedFragmentModule
import com.analytics.pokegostats.view.pokemon.detailed.PokemonDetailedFragmentModule
import com.analytics.pokegostats.view.pokemon.detailed.PokemonDetailedListFragment
import com.analytics.pokegostats.view.pokemon.detailed.PokemonDetailedWeatherListFragment
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