package com.example.weatherzadravec.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey(autoGenerate = false)
    val cityId: Int,
    val name: String,
    val temperature: Double,
    val iconUrl: String,
    val lat: Double,
    val lon: Double,
    val currentLocation: Boolean
)