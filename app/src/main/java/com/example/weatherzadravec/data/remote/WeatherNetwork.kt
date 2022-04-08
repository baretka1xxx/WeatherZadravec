package com.example.weatherzadravec.data.remote

import com.example.weatherzadravec.data.model.Weather
import com.example.weatherzadravec.data.model.WeatherDays
import com.example.weatherzadravec.utils.Constants.API.WEATHER
import com.example.weatherzadravec.utils.Constants.API.WEATHER_DAILY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherNetwork {

    @GET(WEATHER)
    suspend fun getWeather(
        @Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String = "656551190ecb9255bb31d38f3fcdc376", @Query("units") units: String = "metric"
    ): Response<Weather>

    @GET(WEATHER_DAILY)
    suspend fun getWeatherForDays(
        @Query("lat") lat: Double, @Query("lon") lon: Double, @Query("cnt") cnt: Int = 24, @Query("appid") appid: String = "656551190ecb9255bb31d38f3fcdc376", @Query("units") units: String = "metric"
    ): Response<WeatherDays>
}