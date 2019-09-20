package com.example.pokegostats.view.move.detailed

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.entity.PokemonMovesEntity
import kotlinx.coroutines.launch

class PokemonMoveDetailedFragmentViewModel(application: Application, private val repository: PokemonGoStatsRepository)
    : AndroidViewModel(application) {
    var pokemonMoveDetailed: MutableLiveData<PokemonMovesEntity> = MutableLiveData()

    suspend fun getMove(moveName: String) = viewModelScope.launch {
        pokemonMoveDetailed.postValue(repository.getMove(moveName))
    }

    companion object {
        class Factory(private val mApplication: Application, private val repository: PokemonGoStatsRepository)
            : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonMoveDetailedFragmentViewModel(mApplication, repository) as T
            }
        }
    }
}