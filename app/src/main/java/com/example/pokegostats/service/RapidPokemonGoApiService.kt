package com.example.pokegostats.service

import com.example.pokegostats.model.PokemonGoStats
import retrofit2.Response
import retrofit2.http.GET

interface RapidPokemonGoApiService {

    @GET("/pokemon_stats.json")
    suspend fun getRapidPokemonGoStats() : Response<List<PokemonGoStats>>

    // TODO: Implement other Endpoints
//    @GET("/gridpoints/{wfo}/{x},{y}")
//    fun getNWSGridpoints(@Path("wfo") Office: String,
//                         @Path("x") xAxis: Int,
//                         @Path("y") yAxis: Int) : Call<NWSGridPoints>
//
//    @GET("/gridpoints/{wfo}/{x},{y}/forecast")
//    fun getNWSGridpointsForecast(@Path("wfo") Office: String,
//                                 @Path("x") xAxis: Int,
//                                 @Path("y") yAxis: Int) : Call<NWSGridPointsForecast>
//
//    @GET("/gridpoints/{wfo}/{x},{y}/forecast/hourly")
//    fun getNWSGridpointsForecastHourly(@Path("wfo") Office: String,
//                                       @Path("x") xAxis: Int,
//                                       @Path("y") yAxis: Int) : Call<NWSGridPointsForecastHourly>
}