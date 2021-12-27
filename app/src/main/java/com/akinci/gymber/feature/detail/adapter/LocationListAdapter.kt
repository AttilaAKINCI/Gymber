package com.akinci.gymber.feature.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.gymber.R
import com.akinci.gymber.common.helper.LocationProvider
import com.akinci.gymber.data.output.Location
import com.akinci.gymber.databinding.RowLocationBinding

class LocationListAdapter: ListAdapter<Location, LocationListAdapter.LocationViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LocationViewHolder(RowLocationBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class LocationViewHolder(private val binding: RowLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Location) {
            binding.data = data

            with(binding.root.context){
                // we have location info that acquired before
                LocationProvider.lastKnownLocation?.let {
                    binding.locationDistance.text = resources.getString(R.string.distance,
                        LocationProvider.calculateDistanceByKm(
                            data.latitude,
                            data.longitude,
                            it.latitude,
                            it.longitude
                        ).toString()
                    )
                }?: run {
                    binding.locationDistance.text = resources.getString(R.string.distance_unknown)
                }
            }

            binding.executePendingBindings()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}