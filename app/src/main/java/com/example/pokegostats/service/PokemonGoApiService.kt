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

    /**
     * No Model object for this as it was not represented with objects in the response
     * GET - https://pokemon-go1.p.rapidapi.com/weather_boosts.json
     *
     * During different weather certain types will be boosted.
     * When they are boosted Pokemon of the boosted types will be
     * found at a higher level, and moves of that type will be more powerful.
     * This API lists what each weather type boosts. Returns a JSON dict
     * where each key is the weather type and the value is an array of boosted types.
     */
    suspend fun getRapidPokemonGoWeatherBoosts(): HashMap<String, List<String>> {
        val response: Response<HashMap<String, List<String>>> = apiService.getRapidPokemonGoWeatherBoosts()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "getRapidPokemonGoTypes")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoMaxCp(): List<RapidPokemonGoMaxCp> {
        val response: Response<List<RapidPokemonGoMaxCp>> = apiService.getRapidPokemonGoMaxCp()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoCandyEvolve(): HashMap<String, List<RapidPokemonGoCandyEvolve>> {
        val response: Response<HashMap<String, List<RapidPokemonGoCandyEvolve>>> = apiService.getRapidPokemonGoCandyEvolve()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoBuddyDistances(): HashMap<String, List<RapidPokemonGoBuddyDistances>> {
        val response: Response<HashMap<String, List<RapidPokemonGoBuddyDistances>>> = apiService.getRapidPokemonGoBuddyDistances()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoRaidExclusive(): HashMap<String, RapidPokemonGoRaidExclusive> {
        val response: Response<HashMap<String, RapidPokemonGoRaidExclusive>> = apiService.getRapidPokemonGoRaidExclusive()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoNestingPokemon(): HashMap<String, RapidPokemonGoNestingPokemon> {
        val response: Response<HashMap<String, RapidPokemonGoNestingPokemon>> = apiService.getRapidPokemonGoNestingPokemon()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoShinyPokemon(): HashMap<String, RapidPokemonGoShinyPokemon> {
        val response: Response<HashMap<String, RapidPokemonGoShinyPokemon>> = apiService.getRapidPokemonGoShinyPokemon()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoReleasedPokemon(): HashMap<String, RapidPokemonGoReleasedPokemon> {
        val response: Response<HashMap<String, RapidPokemonGoReleasedPokemon>> = apiService.getRapidPokemonGoReleasedPokemon()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoPossibleDittoTypes(): HashMap<String, RapidPokemonGoPossibleDittoTypes> {
        val response: Response<HashMap<String, RapidPokemonGoPossibleDittoTypes>> = apiService.getRapidPokemonGoPossibleDittoTypes()
        handleResponse(response.isSuccessful, response.code(), response.body().toString(), response.message(), "RapidPokemonGoMaxCp")

        return response.body()!!
    }

    suspend fun getRapidPokemonGoEncounterData(): List<RapidPokemonGoEncounterData> {
        val response: Response<List<RapidPokemonGoEncounterData>> = apiService.getRapidPokemonGoEncounterData()
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