package com.example.weatherzadravec.ui.locations

import com.example.weatherzadravec.data.model.Location
import com.example.weatherzadravec.data.repository.LocationRepository
import com.example.weatherzadravec.data.repository.WeatherRepository
import com.example.weatherzadravec.managers.InternetManager
import com.example.weatherzadravec.utils.Constants.API.BASE_ICON
import com.example.weatherzadravec.utils.Constants.API.EXTENSION_ICON
import com.example.weatherzadravec.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocationsPresenter @Inject constructor(
    private val view: LocationsContract.View, private val locationRepository: LocationRepository, private val weatherRepository: WeatherRepository
) : LocationsContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onAddLocationClicked() {
        if(InternetManager.isOnline()) {
            view.navigateToAddLocation()
        } else {
            view.onErrorMessage("Couldn't reach the server. Check your internet connection")
        }
    }

    override fun onLocationClicked(location: Location) {
        if(InternetManager.isOnline()) {
            view.navigateToLocationDetail(location)
        } else {
            view.onErrorMessage("Couldn't reach the server. Check your internet connection")
        }
    }

    override fun fetchWeathers() {
        fetchLocationsRoom()
    }

    override fun onAllowWeatherTrackClicked() {
        view.startForegroundService()
    }

    override fun onDontAllowWeatherTrackClicked() {
        view.closePermissionDialog()
    }

    private fun fetchLocationsRoom() {
        launch {
            val locations = locationRepository.getLocations()
            view.onLocationsLoaded(locations)

            if(InternetManager.isOnline()) {
                fetchWeathers(locations)
            } else {
                view.onErrorMessage("Couldn't reach the server. Check your internet connection")
            }
        }
    }

    private fun fetchWeathers(locations: List<Location>) {
        for(data in locations) {
            fetchWeather(data.lat, data.lon, data.currentLocation)
        }
    }

    private fun fetchWeather(lat: Double, lon: Double, currentLocation: Boolean) {
        launch {
            val weather = weatherRepository.fetchWeather(lat, lon)

            when(weather.status) {
                Status.SUCCESS -> {
                    weather.data?.let {
                        val iconUrl = BASE_ICON + it.weather.first().icon + EXTENSION_ICON
                        insertLocationRoom(Location(it.id, it.name, it.main.temp, iconUrl, it.coord.lat, it.coord.lon, currentLocation))
                    }
                }
                Status.ERROR -> {
                    weather.message?.let { view.onErrorMessage(it) }
                }
            }
        }
    }

    private fun insertLocationRoom(location: Location) {
        launch {
            locationRepository.insertLocation(location)
            val locations = locationRepository.getLocations()
            view.onLocationsLoaded(locations)
        }
    }
}