package com.example.weatherzadravec.ui.locations.addLocation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherzadravec.databinding.ActivityAddLocationBinding
import com.example.weatherzadravec.utils.Constants.API.MAPS_KEY
import com.example.weatherzadravec.utils.Constants.Permission.PERMISSION_LOCATION
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddLocationsActivity @Inject constructor() : AppCompatActivity(), AddLocationsContract.View {

    private lateinit var binding: ActivityAddLocationBinding

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    @Inject lateinit var presenter: AddLocationsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickListeners()
        activityResultListeners()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_LOCATION) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                presenter.locationPermissionGranted()
            }
        }
    }

    private fun activityResultListeners() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {

                result.data?.let { data ->
                    val place = Autocomplete.getPlaceFromIntent(data)

                    place.latLng?.let { latLng ->
                        presenter.onPlaceSelected(latLng)
                    }
                }
            }
        }
    }

    private fun clickListeners() {
        binding.fabCurrentLocation.setOnClickListener {
            presenter.onCurrentLocationClicked()
        }

        binding.fabSearchLocation.setOnClickListener {
            presenter.onSearchLocationClicked()
        }
    }

    override fun checkLocationPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_LOCATION)
        } else {
            presenter.locationPermissionGranted()
        }
    }

    override fun onErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getUserLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val provider = LocationServices.getFusedLocationProviderClient(this)
            provider.lastLocation.addOnCompleteListener {
                val location = it.result
                presenter.getWeather(location.latitude, location.longitude)
            }
        }
    }

    override fun openSearchLocation() {
        Places.initialize(applicationContext, MAPS_KEY)
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        resultLauncher.launch(intent)
    }

    override fun placeAdded() {
        Toast.makeText(this, "Weather place has been added.", Toast.LENGTH_SHORT).show()
        finish()
    }
}