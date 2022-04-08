package com.example.weatherzadravec.data.repository

import com.example.weatherzadravec.data.model.Weather
import com.example.weatherzadravec.data.model.WeatherDays
import com.example.weatherzadravec.data.remote.WeatherNetwork
import com.example.weatherzadravec.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherNetwork: WeatherNetwork) {

    suspend fun fetchWeather(lat: Double, lon: Double): Resource<Weather> {
        return try {
            val response = weatherNetwork.getWeather(lat, lon)

            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null, "An unknown error occurred")
            } else {
                Resource.error(null, "An unknown error occurred")
            }
        } catch(e: Exception) {
            Resource.error(null, "Couldn't reach the server. Check your internet connection")
        }
    }

    suspend fun fetchWeatherForDays(lat: Double, lon: Double): Resource<WeatherDays> {
        return try {
            val response = weatherNetwork.getWeatherForDays(lat, lon)

            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null, "An unknown error occurred")
            } else {
                Resource.error(null, "An unknown error occurred")
            }
        } catch(e: Exception) {
            Resource.error(null, "Couldn't reach the server. Check your internet connection")
        }
    }
}