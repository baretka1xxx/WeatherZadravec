package com.example.weatherzadravec.ui.locations

import android.app.Activity
import com.example.weatherzadravec.data.model.Location
import com.example.weatherzadravec.ui.base.BaseRecyclerViewAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class LocationsModule {

    @Binds
    abstract fun provideView(activity: LocationsActivity): LocationsContract.View

    @Binds
    abstract fun providePresenter(presenter: LocationsPresenter): LocationsContract.Presenter

    @Binds
    abstract fun provideLocationsAdapter(locationsAdapter: LocationsAdapter): BaseRecyclerViewAdapter<Location, LocationsAdapter.LocationsVH>


    companion object {

        @Provides
        fun bindActivity(activity: Activity): LocationsActivity {
            return activity as LocationsActivity
        }
    }
}