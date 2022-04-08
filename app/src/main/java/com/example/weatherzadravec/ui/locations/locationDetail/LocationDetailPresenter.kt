package com.example.weatherzadravec.ui.locations.locationDetail

import com.example.weatherzadravec.data.model.WeatherDays
import com.example.weatherzadravec.data.model.WeatherThreeHour
import com.example.weatherzadravec.data.repository.WeatherRepository
import com.example.weatherzadravec.extensions.toDate
import com.example.weatherzadravec.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocationDetailPresenter @Inject constructor(
    @LocationLat private val locationLat: Double, @LocationLon private val locationLon: Double, private val view: LocationDetailContract.View, private val weatherRepository: WeatherRepository
) : LocationDetailContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onViewCreated() {
        fetchWeather()
    }

    private fun fetchWeather() {
        launch {
            val weather = weatherRepository.fetchWeatherForDays(locationLat, locationLon)

            when(weather.status) {
                Status.SUCCESS -> {
                    weather.data?.let {
                        /* Servis koji sam koristio nema opciju vračanja prognoze po danu. Može vračati ali treba uzeti pro verziju koja se naplaćuje.
                        Zato sam morao malo preraditi podatke u funkciji ispod. Servis koji je ponuđen u zadatku više ne postoji. */
                        val changedList = changeList(it)
                        view.onWeatherDaysLoaded(changedList)
                    }
                }
                Status.ERROR -> {
                    weather.message?.let { view.onErrorMessage(it) }
                }
            }
        }
    }

    private fun changeList(weatherDays: WeatherDays): List<WeatherThreeHour> {
        val weatherDaysList = mutableListOf<WeatherThreeHour>()
        val timeAfter = Calendar.getInstance()
        timeAfter.add(Calendar.DATE, 2)

        weatherDaysList.add(weatherDays.list.first())

        for(data in weatherDays.list) {
            val cal = Calendar.getInstance()
            cal.time = data.dt_txt.toDate()

            if(cal.time < timeAfter.time) {
                val hour = cal[Calendar.HOUR_OF_DAY]

                if(hour == 12) {
                    if(!weatherDaysList.contains(data)) {
                        weatherDaysList.add(data)
                    }
                }
            }
        }

        return weatherDaysList
    }
}