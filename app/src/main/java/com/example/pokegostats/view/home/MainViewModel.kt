package com.example.pokegostats.view.home

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRespository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonEntity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokegostats.service.PokemonGoService
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter
class MainViewModel(application: Application, val service: PokemonGoService) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data
    private val repository: PokemonGoStatsRespository

    val allPokemon: LiveData<List<PokemonEntity>>

    init {
        val pokemonDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonDao()
        repository = PokemonGoStatsRespository(pokemonDao, service)
        allPokemon = repository.allPokemon
    }

    fun populatePokemonTable() = viewModelScope.launch {
        repository.insert()
    }

    companion object {
        class Factory(
            private val mApplication: Application, val service: PokemonGoService) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(mApplication, service) as T
            }
        }
    }
}
