package com.example.weatherzadravec.ui.locations.addLocation

import com.google.android.gms.maps.model.LatLng

interface AddLocationsContract {

    interface View {

        fun onErrorMessage(message: String)

        fun checkLocationPermission()

        fun getUserLocation()

        fun openSearchLocation()

        fun placeAdded()
    }

    interface Presenter {

        fun locationPermissionGranted()

        fun getWeather(lat: Double, lon: Double)

        fun onPlaceSelected(latLon: LatLng)

        fun onCurrentLocationClicked()

        fun onSearchLocationClicked()
    }
}
