package com.example.weatherzadravec.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.weatherzadravec.R
import com.example.weatherzadravec.data.model.Location
import com.example.weatherzadravec.databinding.ItemLocationBinding
import com.example.weatherzadravec.ui.base.BaseRecyclerViewAdapter
import javax.inject.Inject

class LocationsAdapter @Inject constructor() : BaseRecyclerViewAdapter<Location, LocationsAdapter.LocationsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsVH {
        return LocationsVH(
            ItemLocationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class LocationsVH(private val binding: ItemLocationBinding) : BaseRecyclerViewAdapter<Location, LocationsVH>.BaseVH(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener?.onItemClick(this@LocationsAdapter, this, adapterPosition)
            }
        }

        override fun performBind(item: Location) {
            binding.textViewLocationName.text = item.name
            binding.textViewLocationTemp.text = binding.root.context.getString(
                R.string.text_location_temperature, item.temperature.toInt().toString()
            )
            Glide.with(itemView.context).load(item.iconUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.ic_baseline_wb_sunny_24).transform(CenterCrop(), RoundedCorners(100)).into(binding.imageViewWeather)
        }
    }
}