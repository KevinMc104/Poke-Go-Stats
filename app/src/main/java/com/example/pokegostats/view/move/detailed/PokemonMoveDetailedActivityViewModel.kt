package com.example.pokegostats.view.move.detailed

import android.app.Application
import androidx.lifecycle.*
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.entity.PokemonMovesEntity
import com.example.pokegostats.service.PokemonGoApiService
import kotlinx.coroutines.launch

class PokemonMoveDetailedActivityViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        class Factory(
            private val mApplication: Application
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonMoveDetailedActivityViewModel(mApplication) as T
            }
        }
    }
}