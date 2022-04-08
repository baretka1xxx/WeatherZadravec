package com.example.weatherzadravec.ui.locations

import com.example.weatherzadravec.data.model.Location


interface LocationsContract {

    interface View {

        fun onErrorMessage(message: String)

        fun navigateToAddLocation()

        fun navigateToLocationDetail(location: Location)

        fun onLocationsLoaded(locations: List<Location>)

        fun startForegroundService()

        fun closePermissionDialog()
    }

    interface Presenter {

        fun onAddLocationClicked()

        fun onLocationClicked(location: Location)

        fun fetchWeathers()

        fun onAllowWeatherTrackClicked()

        fun onDontAllowWeatherTrackClicked()
    }
}
