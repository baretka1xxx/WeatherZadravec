package com.example.weatherzadravec.ui.locations.locationDetail

import com.example.weatherzadravec.data.model.WeatherThreeHour

interface LocationDetailContract {

    interface View {

        fun onErrorMessage(message: String)

        fun onWeatherDaysLoaded(weatherDays: List<WeatherThreeHour>)
    }

    interface Presenter {

        fun onViewCreated()


    }
}
