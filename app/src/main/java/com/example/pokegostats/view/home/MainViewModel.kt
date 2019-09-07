package com.example.pokegostats.view.home

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonAndPokemonForms
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonFormsAndPokemonTypes
import com.example.pokegostats.service.PokemonGoApiService
import kotlinx.coroutines.launch


// Class extends AndroidViewModel and requires application as a parameter
class MainViewModel(application: Application, val service: PokemonGoApiService) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data
    private val repository: PokemonGoStatsRepository

    val allPokemon: LiveData<List<PokemonEntity>>
    val allPokemonForms: LiveData<List<PokemonAndPokemonForms>>
    val allPokemonTypes: LiveData<List<PokemonFormsAndPokemonTypes>>

    init {
        val pokemonDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonDao()
        val pokemonAndPokemonFormsDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonAndPokemonFormsDao()
        val pokemonFormsAndPokemonTypesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonFormsAndPokemonTypesDao()
        repository = PokemonGoStatsRepository(pokemonDao, pokemonAndPokemonFormsDao, pokemonFormsAndPokemonTypesDao, service)
        allPokemon = repository.allPokemon
        allPokemonForms = repository.allPokemonForms
        allPokemonTypes = repository.allPokemonTypes
    }

    fun populatePokemonTable() = viewModelScope.launch {
        repository.insertPokemon()
    }

    companion object {
        class Factory(
            private val mApplication: Application, val service: PokemonGoApiService) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(mApplication, service) as T
            }
        }
    }
}
