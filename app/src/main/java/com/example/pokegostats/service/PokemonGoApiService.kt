package com.example.pokegostats.service

import android.content.Context
import android.util.Log
import com.example.pokegostats.model.PokemonGoStatsResult
import com.example.pokegostats.model.events.RapidPokemonGoStatsEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonGoApiService @Inject constructor(private val context: Context, private val pokemonGoService: PokemonGoService){
    fun getPokemonGoStats() {
        GlobalScope.launch {pokemonGoService.getPokemonGoStats()}
    }

}

@Singleton
class PokemonGoService @Inject constructor(private val apiService: RapidPokemonGoApiService) {
    fun getPokemonGoStats(){
        val response: Response<PokemonGoStatsResult> = apiService.getRapidPokemonGoStats().execute()
        if(response.isSuccessful) {
            Log.i("getPokemonGoStats", response.body().toString())
            EventBus.getDefault().postSticky(RapidPokemonGoStatsEvent(response.body()!!, isSuccess = true))
        } else {
            EventBus.getDefault().postSticky(RapidPokemonGoStatsEvent(isSuccess = false, error = response.message()))
        }

    }

}