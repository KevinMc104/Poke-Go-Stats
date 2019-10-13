package com.analytics.pokegostats.view.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Class extends AndroidViewModel and requires application as a parameter
class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        class Factory(
            private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(mApplication) as T
            }
        }
    }
}
