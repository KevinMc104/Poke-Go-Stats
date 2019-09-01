package com.example.pokegostats.service

import android.util.Log
import com.example.pokegostats.model.PokemonGoStats
import com.example.pokegostats.model.events.RapidPokemonGoStatsEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonGoService @Inject constructor(private val apiService: RapidPokemonGoApiService) {
    suspend fun getPokemonGoStats(): List<PokemonGoStats> {
        return apiService.getRapidPokemonGoStats()
//        val response: Response<List<PokemonGoStats>> = apiService.getRapidPokemonGoStats().execute()
//        if(response.isSuccessful) {
//            Log.i("getPokemonGoStats", response.body().toString())
//            EventBus.getDefault().postSticky(RapidPokemonGoStatsEvent(response.body()!![0], isSuccess = true))
//            return response.body()!!
//        } else {
//            EventBus.getDefault().postSticky(RapidPokemonGoStatsEvent(isSuccess = false, error = response.message()))
//            return response.body()!!
//        }
    }

}