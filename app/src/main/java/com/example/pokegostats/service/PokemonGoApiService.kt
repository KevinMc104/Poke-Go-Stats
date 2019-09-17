package com.example.pokegostats.service

import android.util.Log
import com.example.pokegostats.model.*
import com.example.pokegostats.model.exception.NotFoundException
import com.example.pokegostats.model.exception.ServerErrorException
import com.example.pokegostats.model.exception.UnauthenticatedException
import com.example.pokegostats.model.exception.UnauthorizedException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonGoApiService @Inject constructor(private val apiService: RapidPokemonGoApiService) {
    suspend fun getRapidPokemonGoStats(): List<RapidPokemonGoStats> {
        val response: Response<List<RapidPokemonGoStats>> = apiService.getRapidPokemonGoStats()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "getRapidPokemonGoStats")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoTypes(): List<RapidPokemonGoTypes> {
        val response: Response<List<RapidPokemonGoTypes>> = apiService.getRapidPokemonGoTypes()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "getRapidPokemonGoTypes")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoWeatherBoosts(): RapidPokemonGoWeatherBoosts {
        val response: Response<RapidPokemonGoWeatherBoosts> = apiService.getRapidPokemonGoWeatherBoosts()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "getRapidPokemonGoTypes")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoMaxCp(): List<RapidPokemonGoMaxCp> {
        val response: Response<List<RapidPokemonGoMaxCp>> = apiService.getRapidPokemonGoMaxCp()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoCandyEvolve(): RapidPokemonGoCandyEvolve {
        val response: Response<RapidPokemonGoCandyEvolve> = apiService.getRapidPokemonGoCandyEvolve()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoFastMoves(): List<RapidPokemonGoFastMoves> {
        val response: Response<List<RapidPokemonGoFastMoves>> = apiService.getRapidPokemonGoFastMoves()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "getRapidPokemonGoFastMoves")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoChargedMoves(): List<RapidPokemonGoChargedMoves> {
        val response: Response<List<RapidPokemonGoChargedMoves>> = apiService.getRapidPokemonGoChargedMoves()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "getRapidPokemonGoFastMoves")

        return response.body()!!
    }

    private fun handleResponse(isSuccessful: Boolean, code: Int, body: String, message: String, endpointMessage: String) {
        if(isSuccessful) {
            Log.i(endpointMessage, body)
        } else {
            handleErrors(code, message)
        }
    }

    private fun handleErrors(code: Int, message: String) {
        when(code) {
            401 -> throw UnauthenticatedException("Error Code 401: +$message")
            403 -> throw UnauthorizedException("Error Code 403: +$message")
            404 -> throw NotFoundException("Error Code 404: +$message")
            500 -> throw ServerErrorException("Server is down - Code: 500 - +$message")
        }
    }
}