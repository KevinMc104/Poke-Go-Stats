package com.example.pokegostats.view.move.detailed

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonMovesEntity
import com.example.pokegostats.service.PokemonGoApiService
import kotlinx.coroutines.launch

class PokemonMoveDetailedViewModel(application: Application, val service: PokemonGoApiService) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data
    private val repository: PokemonGoStatsRepository
    var pokemonMoveDetailed: MutableLiveData<PokemonMovesEntity> = MutableLiveData()

    init {
        val pokemonDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonDao()
        val pokemonMovesDao = PokemonGoStatsRoomDatabase.getDatabase(application, viewModelScope).pokemonMovesDao()
        repository = PokemonGoStatsRepository(pokemonDao, pokemonMovesDao, service)
    }

    suspend fun getMove(moveName: String) = viewModelScope.launch {
        pokemonMoveDetailed.postValue(repository.getMove(moveName))
    }

    companion object {
        class Factory(
            private val mApplication: Application, private val service: PokemonGoApiService
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonMoveDetailedViewModel(mApplication, service) as T
            }
        }
    }
}