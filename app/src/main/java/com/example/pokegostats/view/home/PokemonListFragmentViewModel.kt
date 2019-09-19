package com.example.pokegostats.view.home

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.example.pokegostats.service.PokemonGoApiService

// Class extends AndroidViewModel and requires application as a parameter
class PokemonListFragmentViewModel(application: Application, val service: PokemonGoApiService) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data
    private val repository: PokemonGoStatsRepository

    val allPokemonFormsTypesWeatherBoosts: LiveData<List<PokemonFormsTypesWeatherBoosts>>

    init {
        val pokemonDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonDao()
        val pokemonMovesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonMovesDao()
        repository = PokemonGoStatsRepository(pokemonDao, pokemonMovesDao, service)
        allPokemonFormsTypesWeatherBoosts = repository.allPokemonFormsTypesWeatherBoosts
    }

    suspend fun populatePokemonTable() {
        repository.insertPokemon()
    }

    suspend fun updateDetailedPokemonData() {
        repository.updateDetailedPokemonData()
    }

    companion object {
        class Factory(
            private val mApplication: Application, private val service: PokemonGoApiService) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonListFragmentViewModel(mApplication, service) as T
            }
        }
    }
}
