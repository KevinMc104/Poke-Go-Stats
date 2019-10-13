package com.analytics.pokegostats.view.move.detailed

import android.app.Application
import androidx.lifecycle.*

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