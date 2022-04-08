package com.example.weatherzadravec.data.repository

import com.example.weatherzadravec.data.local.db.dao.LocationsDao
import com.example.weatherzadravec.data.model.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val locationsDao: LocationsDao) {

     suspend fun insertLocation(location: Location) {
        locationsDao.insertLocation(location)
    }

     suspend fun getLocations(): List<Location> {
        return locationsDao.getLocations()
    }
}