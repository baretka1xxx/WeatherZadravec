package com.example.weatherzadravec.utils

class Constants {

    object SharePreference {
        const val ALLOW_BAD_WEATHER_TRACK = "Allow bad weather track"
    }

    object API {
        const val WEATHER = "/data/2.5/weather"
        const val WEATHER_DAILY = "/data/2.5/forecast"
        const val MAPS_KEY = "AIzaSyCELq_r3v-rIuQeE5M4pQ-pjE0EsSKb4_w"
        const val BASE_ICON = "https://openweathermap.org/img/wn/"
        const val EXTENSION_ICON = ".png"
    }

    object Permission {
        const val PERMISSION_LOCATION = 1
    }

    object Notifications{
         const val CHANNEL_ID = "channel 1"
         const val CHANNEL_NAME = "my_channel"
    }
}