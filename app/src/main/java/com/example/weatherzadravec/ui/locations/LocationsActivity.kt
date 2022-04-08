package com.example.weatherzadravec.ui.locations

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherzadravec.R
import com.example.weatherzadravec.data.model.Location
import com.example.weatherzadravec.data.remote.MyService
import com.example.weatherzadravec.databinding.ActivityLocationsBinding
import com.example.weatherzadravec.databinding.DialogForegroundServiceBinding
import com.example.weatherzadravec.managers.SharePreferenceManager
import com.example.weatherzadravec.ui.base.BaseRecyclerViewAdapter
import com.example.weatherzadravec.ui.locations.addLocation.AddLocationsActivity
import com.example.weatherzadravec.ui.locations.locationDetail.LocationDetailActivity
import com.example.weatherzadravec.utils.Constants.SharePreference.ALLOW_BAD_WEATHER_TRACK
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val EXTRA_KEY_LOCATION_LAT = "com.example.weatherzadravec.ui.locations.EXTRA_KEY_LOCATION_LAT"
const val EXTRA_KEY_LOCATION_LON = "com.example.weatherzadravec.ui.locations.EXTRA_KEY_LOCATION_LON"

@AndroidEntryPoint
class LocationsActivity @Inject constructor() : AppCompatActivity(), LocationsContract.View {

    private lateinit var binding: ActivityLocationsBinding
    private lateinit var bindingDialog: DialogForegroundServiceBinding
    private var mAlertDialog: AlertDialog? = null

    @Inject lateinit var presenter: LocationsContract.Presenter

    @Inject lateinit var locationsAdapter: BaseRecyclerViewAdapter<Location, LocationsAdapter.LocationsVH>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationsBinding.inflate(layoutInflater)
        bindingDialog = DialogForegroundServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickListeners()
        adapterHandler()
        checkServicePermission()
    }

    override fun onResume() {
        super.onResume()

        presenter.fetchWeathers()
    }

    private fun clickListeners() {
        binding.fabAddLocation.setOnClickListener {
            presenter.onAddLocationClicked()
        }

        bindingDialog.buttonAllow.setOnClickListener {
            presenter.onAllowWeatherTrackClicked()
        }

        bindingDialog.buttonDontAllow.setOnClickListener {
            presenter.onDontAllowWeatherTrackClicked()
        }
    }

    private fun checkServicePermission() {
        if(SharePreferenceManager.getBoolean(ALLOW_BAD_WEATHER_TRACK)) {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        } else {
            openPermissionDialog()
        }
    }

    override fun onErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToAddLocation() {
        mAlertDialog?.dismiss()
        val intent = Intent(this, AddLocationsActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToLocationDetail(location: Location) {
        val intent = Intent(this, LocationDetailActivity::class.java)
        intent.putExtra(EXTRA_KEY_LOCATION_LAT, location.lat)
        intent.putExtra(EXTRA_KEY_LOCATION_LON, location.lon)
        startActivity(intent)
    }

    override fun onLocationsLoaded(locations: List<Location>) {
        locationsAdapter.items = locations
    }

    override fun startForegroundService() {
        mAlertDialog?.dismiss()
        SharePreferenceManager.setBoolean(ALLOW_BAD_WEATHER_TRACK, true)
        val intent = Intent(this, MyService::class.java)
        startService(intent)
    }

    override fun closePermissionDialog() {
        mAlertDialog?.dismiss()
    }

    private fun adapterHandler() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = locationsAdapter
        }

        locationsAdapter.listener = object : BaseRecyclerViewAdapter.OnItemClickListener() {

            override fun onItemClick(
                adapter: BaseRecyclerViewAdapter<*, *>, viewHolder: RecyclerView.ViewHolder, position: Int
            ) {
                super.onItemClick(adapter, viewHolder, position)
                presenter.onLocationClicked(adapter.getItemAt(position) as Location)
            }
        }
    }

    private fun openPermissionDialog() {
        val mBuilder = MaterialAlertDialogBuilder(this).setView(bindingDialog.root).setTitle(R.string.allow_weather_track)

        if(bindingDialog.root.parent == null) {
            mAlertDialog = mBuilder.show()
        }

        mAlertDialog?.setOnDismissListener {
            (bindingDialog.root.parent as ViewGroup).removeView(bindingDialog.root)
        }
    }
}