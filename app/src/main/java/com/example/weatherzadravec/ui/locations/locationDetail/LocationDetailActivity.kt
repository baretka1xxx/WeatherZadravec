package com.example.weatherzadravec.ui.locations.locationDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.weatherzadravec.R
import com.example.weatherzadravec.data.model.WeatherThreeHour
import com.example.weatherzadravec.databinding.ActivityLocationDetailBinding
import com.example.weatherzadravec.extensions.toDayOfWeek
import com.example.weatherzadravec.utils.Constants.API.BASE_ICON
import com.example.weatherzadravec.utils.Constants.API.EXTENSION_ICON
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationDetailActivity @Inject constructor() : AppCompatActivity(), LocationDetailContract.View {

    private lateinit var binding: ActivityLocationDetailBinding

    @Inject lateinit var presenter: LocationDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onViewCreated()
    }

    override fun onErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onWeatherDaysLoaded(weatherDays: List<WeatherThreeHour>) {
        val iconUrlToday = BASE_ICON + weatherDays[0].weather.first().icon + EXTENSION_ICON
        val iconUrlTomorrow = BASE_ICON + weatherDays[1].weather.first().icon + EXTENSION_ICON
        val iconUrlTomorrowAfter = BASE_ICON + weatherDays[2].weather.first().icon + EXTENSION_ICON

        binding.textViewDateToday.text = weatherDays[0].dt_txt.toDayOfWeek()
        binding.textViewDateTomorrow.text = weatherDays[1].dt_txt.toDayOfWeek()
        binding.textViewDateTomorrowAfter.text = weatherDays[2].dt_txt.toDayOfWeek()
        binding.textViewTempToday.text = getString(R.string.text_temperature, weatherDays[0].main.temp.toInt().toString())
        binding.textViewTempTomorrow.text = getString(R.string.text_temperature, weatherDays[1].main.temp.toInt().toString())
        binding.textViewTempTomorrowAfter.text = getString(R.string.text_temperature, weatherDays[2].main.temp.toInt().toString())

        Glide.with(this).load(iconUrlToday).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.ic_baseline_wb_sunny_24).transform(CenterCrop()).into(binding.imageViewToday)
        Glide.with(this).load(iconUrlTomorrow).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.ic_baseline_wb_sunny_24).transform(CenterCrop()).into(binding.imageViewTomorrow)
        Glide.with(this).load(iconUrlTomorrowAfter).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.ic_baseline_wb_sunny_24).transform(CenterCrop()).into(binding.imageViewTomorrowAfter)
    }
}