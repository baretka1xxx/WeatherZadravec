package com.example.weatherzadravec.data.remote

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.content.ContextCompat
import com.example.weatherzadravec.R
import com.example.weatherzadravec.data.local.db.dao.LocationsDao
import com.example.weatherzadravec.data.repository.WeatherRepository
import com.example.weatherzadravec.di.WeatherZadravecApplication.Companion.context
import com.example.weatherzadravec.utils.Constants.Notifications.CHANNEL_ID
import com.example.weatherzadravec.utils.Constants.Notifications.CHANNEL_NAME
import com.example.weatherzadravec.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyService : Service() {

    @Inject lateinit var dao: LocationsDao

    @Inject lateinit var weatherRepository: WeatherRepository

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        startForeground()
    }

    private fun checkBadWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val locations = dao.getLocations()
            val currentLocation = locations.firstOrNull { it.currentLocation }

            currentLocation?.let {
                fetchWeather(currentLocation.lat, currentLocation.lon)
            }
        }
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val weather = weatherRepository.fetchWeather(lat, lon)

            when(weather.status) {
                Status.SUCCESS -> {
                    weather.data?.let {
                        if(it.weather.first().id in 200..799) {
                            notifyBadWeather()
                        }
                    }
                }
                Status.ERROR -> { //
                }
            }
        }
    }

    private fun startForeground() {
        val channelId = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service", "My Background Service")
        } else {
            ""
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true).setSmallIcon(R.mipmap.ic_launcher).setPriority(PRIORITY_MIN).setCategory(Notification.CATEGORY_SERVICE).build()
        startForeground(101, notification)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                checkBadWeather()
                mainHandler.postDelayed(this, 1800000)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }

    private fun notifyBadWeather() {
        val notificationID = 1
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(mChannel)
        }

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.ic_baseline_wb_sunny_24).setContentTitle("title").setContentText("message").setAutoCancel(true).setColor(ContextCompat.getColor(context, R.color.colorPrimary1))
        notificationManager.notify(notificationID, mBuilder.build())
    }
}