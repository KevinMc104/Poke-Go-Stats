package com.example.pokegostats.service

import android.util.Log
import com.example.pokegostats.model.PokemonGoStats
import com.example.pokegostats.model.exception.NotFoundException
import com.example.pokegostats.model.exception.ServerErrorException
import com.example.pokegostats.model.exception.UnauthenticatedException
import com.example.pokegostats.model.exception.UnauthorizedException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PokemonGoService @Inject constructor(private val apiService: RapidPokemonGoApiService) {
    suspend fun getPokemonGoStats(): List<PokemonGoStats> {
        val response: Response<List<PokemonGoStats>> = apiService.getRapidPokemonGoStats()
        if(response.isSuccessful) {
            Log.i("getPokemonGoStats", response.body().toString())
        } else {
            handleErrors(response)
        }
        return response.body()!!
    }

    private fun handleErrors(response: Response<List<PokemonGoStats>>) {
        when(response.code()) {
            401 -> throw UnauthenticatedException("Error Code 401:" + response.message())
            403 -> throw UnauthorizedException("Error Code 403:" + response.message())
            404 -> throw NotFoundException("Error Code 404: " + response.message())
            500 -> throw ServerErrorException("Server is down - Code: 500 - " + response.message())
        }
    }
}