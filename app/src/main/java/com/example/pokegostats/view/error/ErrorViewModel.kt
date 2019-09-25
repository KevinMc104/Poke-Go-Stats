package com.example.pokegostats.view.error

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Class extends AndroidViewModel and requires application as a parameter
class ErrorViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        class Factory(
            private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ErrorViewModel(mApplication) as T
            }
        }
    }
}