package com.akinci.gymber.feature.dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.akinci.gymber.R
import com.akinci.gymber.common.helper.DistanceCalculator
import com.akinci.gymber.data.output.Partner
import com.akinci.gymber.databinding.RowGymBinding
import timber.log.Timber

class GymCardAdapter(
    context: Context,
    var items: MutableList<Partner>,
    val onDataLoaded :(()->Unit)?,
    var flingAngleProvider: ((Float)->Unit)? = null
): ArrayAdapter<Partner>(context, R.layout.row_gym, items) {

    override fun getCount() = items.size

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowGymBinding.inflate(layoutInflater, parent, false)

        val data = items[position]

        // set data fields to row binding.
        binding.gymImageView.load(data.header_image["desktop"]) {
            crossfade(true)
            listener(object : ImageRequest.Listener{
                override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
                    super.onSuccess(request, metadata)

                    val distance = DistanceCalculator.calculateDistanceByKm(
                        data.locations[0].latitude,
                        data.locations[0].longitude,
                        41.119452,
                        28.954410
                    )
                    binding.gymInfoTextView.text = "${data.name} -- Distance: $distance"
                    onDataLoaded?.invoke()
                }
            })
        }

        if(position == 0){
            // bind flint angle listener to top element
            flingAngleProvider = {
                /** Fling angle comes between range of -1 and 1
                 * [ -1,0 )  -> dislikeHooverImageView should work
                 * ( 0,1 ]  -> likeHooverImageView should work
                 * **/
                if(it >= -1 && it <= 1){
                    Timber.d("fling angle= $it")
                    if(it < 0){
                        binding.likeHooverImageView.alpha = 0f
                        binding.dislikeHooverImageView.alpha = -it
                    }else{
                        binding.likeHooverImageView.alpha = it
                        binding.dislikeHooverImageView.alpha = 0f
                    }
                }
            }
        }

        return binding.root
    }
}