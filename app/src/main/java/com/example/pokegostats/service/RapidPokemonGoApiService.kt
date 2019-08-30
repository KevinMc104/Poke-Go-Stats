package com.example.pokegostats.service

import com.example.pokegostats.model.PokemonGoStatsResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RapidPokemonGoApiService {

    @Headers("x-rapidapi-key:f3ea559106msh45663307fe63d61p1b344bjsn8c047df9ffab; x-rapidapi-host:pokemon-go1.p.rapidapi.com")
    @GET("/pokemon_stats.json")
    fun getRapidPokemonGoStats() : Call<PokemonGoStatsResult>

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