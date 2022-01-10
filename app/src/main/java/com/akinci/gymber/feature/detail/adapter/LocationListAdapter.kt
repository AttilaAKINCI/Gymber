package com.akinci.gymber.feature.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.gymber.R
import com.akinci.gymber.data.output.Address
import com.akinci.gymber.databinding.RowLocationBinding

class LocationListAdapter: ListAdapter<Address, LocationListAdapter.LocationViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LocationViewHolder(RowLocationBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class LocationViewHolder(private val binding: RowLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Address) {
            binding.data = data

            with(binding.root.context){
                // we have location info that acquired before
                binding.locationDistance.text = if(data.distance.isNotBlank()){
                    resources.getString(R.string.distance,data.distance)
                }else{
                    resources.getString(R.string.distance_unknown)
                }
            }

            binding.executePendingBindings()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }
}