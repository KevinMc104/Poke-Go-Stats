package com.example.pokegostats.view.pokemon.detailed

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes
import com.example.pokegostats.service.PokemonGoApiService
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter
class PokemonDetailedViewModel(application: Application, val service: PokemonGoApiService, pokemonId: Int) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data
    private val repository: PokemonGoStatsRepository
    var pokemonDetailed: MutableLiveData<PokemonAndFormsAndTypes> = MutableLiveData()

    init {
        val pokemonDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonDao()
        val pokemonMovesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonMovesDao()
        val pokemonAndFormsAndTypesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonAndFormsAndTypesDao()
        repository = PokemonGoStatsRepository(pokemonDao, pokemonMovesDao, pokemonAndFormsAndTypesDao, service)
    }

    suspend fun getPokemon(pokemonId: Int) = viewModelScope.launch {
        pokemonDetailed.postValue(repository.getPokemon(pokemonId))
    }

    companion object {
        class Factory(
            private val mApplication: Application, private val service: PokemonGoApiService, private val pokemonId: Int
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonDetailedViewModel(mApplication, service, pokemonId) as T
            }
        }
    }
}