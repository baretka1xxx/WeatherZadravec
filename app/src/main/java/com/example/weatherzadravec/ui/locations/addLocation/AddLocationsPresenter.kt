package com.example.weatherzadravec.ui.locations.addLocation

import com.example.weatherzadravec.data.model.Location
import com.example.weatherzadravec.data.repository.LocationRepository
import com.example.weatherzadravec.data.repository.WeatherRepository
import com.example.weatherzadravec.utils.Constants.API.BASE_ICON
import com.example.weatherzadravec.utils.Constants.API.EXTENSION_ICON
import com.example.weatherzadravec.utils.Status
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddLocationsPresenter @Inject constructor(
    private val view: AddLocationsContract.View, private val weatherRepository: WeatherRepository, private val locationRepository: LocationRepository
) : AddLocationsContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun locationPermissionGranted() {
        view.getUserLocation()
    }

    override fun getWeather(lat: Double, lon: Double) {
        fetchWeather(lat, lon, true)
    }

    override fun onPlaceSelected(latLon: LatLng) {
        fetchWeather(latLon.latitude, latLon.longitude)
    }

    override fun onCurrentLocationClicked() {
        view.checkLocationPermission()
    }

    override fun onSearchLocationClicked() {
        view.openSearchLocation()
    }

    private fun fetchWeather(lat: Double, lon: Double, currentLocation: Boolean = false) {
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
            view.placeAdded()
        }
    }
}