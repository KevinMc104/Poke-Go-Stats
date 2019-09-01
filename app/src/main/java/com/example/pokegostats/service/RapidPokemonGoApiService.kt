package com.example.pokegostats.service

import com.example.pokegostats.model.PokemonGoStatsResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RapidPokemonGoApiService {

    @Headers(
        "x-rapidapi-key:1179c81d02mshce35badb04cdbe5p1f2053jsn371b1a7b02dd",
        "x-rapidapi-host:pokemon-go1.p.rapidapi.com"
    )
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