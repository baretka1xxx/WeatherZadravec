package com.example.weatherzadravec.ui.locations.locationDetail

import android.app.Activity
import com.example.weatherzadravec.ui.locations.EXTRA_KEY_LOCATION_LAT
import com.example.weatherzadravec.ui.locations.EXTRA_KEY_LOCATION_LON
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class)
abstract class LocationDetailModule {

    @Binds
    abstract fun provideView(activity: LocationDetailActivity): LocationDetailContract.View

    @Binds
    abstract fun providePresenter(presenter: LocationDetailPresenter): LocationDetailContract.Presenter

    companion object {

        @Provides
        fun bindActivity(activity: Activity): LocationDetailActivity {
            return activity as LocationDetailActivity
        }

        @Provides
        @LocationLat
        fun provideLocationLat(activity: Activity): Double {
            return (activity as LocationDetailActivity).intent.getDoubleExtra(
                EXTRA_KEY_LOCATION_LAT, 0.0
            )
        }

        @Provides
        @LocationLon
        fun provideLocationLon(activity: Activity): Double {
            return (activity as LocationDetailActivity).intent.getDoubleExtra(
                EXTRA_KEY_LOCATION_LON, 0.0
            )
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationLat

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationLon