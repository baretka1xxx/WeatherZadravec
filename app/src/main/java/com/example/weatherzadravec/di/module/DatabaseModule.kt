package com.example.weatherzadravec.di.module

import android.content.Context
import androidx.room.Room
import com.example.weatherzadravec.data.local.db.LocationsDatabase
import com.example.weatherzadravec.data.local.db.dao.LocationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocationsDao(locationsDatabase: LocationsDatabase): LocationsDao {
        return locationsDatabase.locationsDao
    }

    @Provides
    @Singleton
    fun provideLocationsDatabase(@ApplicationContext context: Context): LocationsDatabase {
        return Room.databaseBuilder(
            context.applicationContext, LocationsDatabase::class.java, "locations_db"
        ).build()
    }
}