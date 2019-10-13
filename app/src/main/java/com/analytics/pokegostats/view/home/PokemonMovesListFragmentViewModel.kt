package com.analytics.pokegostats.view.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.analytics.pokegostats.room.PokemonGoStatsRepository

// Class extends AndroidViewModel and requires application as a parameter
class PokemonMovesListFragmentViewModel(application: Application, val repository: PokemonGoStatsRepository)
    : AndroidViewModel(application) {
    val allPokemonMoves = repository.allPokemonMoves

    companion object {
        class Factory(private val mApplication: Application, private val repository: PokemonGoStatsRepository)
            : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonMovesListFragmentViewModel(mApplication, repository) as T
            }
        }
    }
}
