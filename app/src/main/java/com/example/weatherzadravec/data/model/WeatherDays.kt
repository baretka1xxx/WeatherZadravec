package com.example.weatherzadravec.data.model

data class WeatherDays(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherThreeHour>,
    val message: Int
)

data class WeatherThreeHour(
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val visibility: Int,
    val weather: List<WeatherDetails>,
)

data class WeatherDetails(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class City(
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)