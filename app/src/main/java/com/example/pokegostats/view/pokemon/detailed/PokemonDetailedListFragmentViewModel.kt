package com.example.pokegostats.view.pokemon.detailed

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter
class PokemonDetailedListFragmentViewModel(application: Application, private val repository: PokemonGoStatsRepository)
    : AndroidViewModel(application) {
    var pokemonDetailed: MutableLiveData<PokemonFormsTypesWeatherBoosts> = MutableLiveData()

    suspend fun getPokemon(pokemonId: Int, pokemonFormId: Int) = viewModelScope.launch {
        pokemonDetailed.postValue(repository.getPokemon(pokemonId, pokemonFormId))
    }

    companion object {
        class Factory(private val mApplication: Application, private val repository: PokemonGoStatsRepository)
            : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonDetailedListFragmentViewModel(mApplication, repository) as T
            }
        }
    }
}