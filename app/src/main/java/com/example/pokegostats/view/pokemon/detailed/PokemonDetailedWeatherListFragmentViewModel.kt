package com.example.pokegostats.view.pokemon.detailed

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.example.pokegostats.service.PokemonGoApiService
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter
class PokemonDetailedWeatherListFragmentViewModel(application: Application, val service: PokemonGoApiService) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data
    private val repository: PokemonGoStatsRepository
    var pokemonDetailed: MutableLiveData<PokemonFormsTypesWeatherBoosts> = MutableLiveData()

    init {
        val pokemonDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonDao()
        val pokemonMovesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonMovesDao()
        repository = PokemonGoStatsRepository(pokemonDao, pokemonMovesDao, service)
    }

    suspend fun getPokemon(pokemonId: Int, pokemonFormId: Int) = viewModelScope.launch {
        pokemonDetailed.postValue(repository.getPokemon(pokemonId, pokemonFormId))
    }

    companion object {
        class Factory(
            private val mApplication: Application, private val service: PokemonGoApiService
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonDetailedWeatherListFragmentViewModel(mApplication, service) as T
            }
        }
    }
}