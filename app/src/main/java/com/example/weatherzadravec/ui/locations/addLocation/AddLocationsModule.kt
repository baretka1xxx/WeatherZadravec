package com.example.weatherzadravec.ui.locations.addLocation

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AddLocationsModule {

    @Binds
    abstract fun provideView(activity: AddLocationsActivity): AddLocationsContract.View

    @Binds
    abstract fun providePresenter(presenter: AddLocationsPresenter): AddLocationsContract.Presenter

    companion object {

        @Provides
        fun bindActivity(activity: Activity): AddLocationsActivity {
            return activity as AddLocationsActivity
        }
    }
}