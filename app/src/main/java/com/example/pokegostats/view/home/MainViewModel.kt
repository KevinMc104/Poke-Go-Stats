package com.example.pokegostats.view.home

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes
import com.example.pokegostats.room.entity.PokemonEntity
import com.example.pokegostats.room.entity.PokemonMovesEntity
import com.example.pokegostats.service.PokemonGoApiService
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter
class MainViewModel(application: Application, val service: PokemonGoApiService) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data
    private val repository: PokemonGoStatsRepository

    val allPokemon: LiveData<List<PokemonEntity>>
    val allPokemonMoves: LiveData<List<PokemonMovesEntity>>
    val allPokemonFormsAndTypes: LiveData<List<PokemonAndFormsAndTypes>>

    init {
        val pokemonDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonDao()
        val pokemonMovesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonMovesDao()
        val pokemonAndFormsAndTypesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonAndFormsAndTypesDao()
        repository = PokemonGoStatsRepository(pokemonDao, pokemonMovesDao, pokemonAndFormsAndTypesDao, service)
        allPokemon = repository.allPokemon
        allPokemonMoves = repository.allPokemonMoves
        allPokemonFormsAndTypes = repository.allPokemonFormsAndTypes
    }

    fun populatePokemonTable() = viewModelScope.launch {
        repository.insertPokemon()
    }

    fun populatePokemonMovesTable() = viewModelScope.launch {
        repository.insertMoves()
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
