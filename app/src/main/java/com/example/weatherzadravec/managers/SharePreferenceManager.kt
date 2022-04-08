package com.example.weatherzadravec.managers

import android.content.Context
import com.example.weatherzadravec.di.WeatherZadravecApplication

class SharePreferenceManager{

    companion object {

        private val preference = WeatherZadravecApplication.context.getSharedPreferences("PREF", Context.MODE_PRIVATE)

        fun getBoolean(key: String): Boolean {
            return preference.getBoolean(key, false)
        }

        fun setBoolean(key: String, data: Boolean) {
            val editor = preference.edit()
            editor.putBoolean(key, data)
            editor.apply()
        }
    }
}