package com.example.weatherzadravec.di

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp internal class WeatherZadravecApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: Application? = null
        val context: Context
            get() = instance!!.applicationContext
    }
}