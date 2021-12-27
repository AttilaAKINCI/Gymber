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

            binding.locationDistance.text = binding.root.context.
                resources.getString(R.string.distance,
                    LocationProvider.calculateDistanceByKm(
                        data.latitude,
                        data.longitude,
                        41.119452,
                        28.954410
                    ).toString()
                )

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