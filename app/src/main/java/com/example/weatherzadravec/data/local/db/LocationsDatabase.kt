package com.example.weatherzadravec.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherzadravec.data.local.db.dao.LocationsDao
import com.example.weatherzadravec.data.model.Location

@Database(version = 1, entities = [Location::class])
abstract class LocationsDatabase : RoomDatabase() {
    abstract val locationsDao: LocationsDao
}