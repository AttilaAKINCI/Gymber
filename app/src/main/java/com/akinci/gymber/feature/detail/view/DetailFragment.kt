package com.akinci.gymber.feature.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.akinci.gymber.R
import com.akinci.gymber.common.helper.DateTimeHelper
import com.akinci.gymber.databinding.FragmentDetailBinding
import com.akinci.gymber.feature.dashboard.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val viewModel : DashboardViewModel by activityViewModels()

    private val detailFragmentData by lazy { viewModel.getTopItem() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /** Initialization of ViewBinding, not need for DataBinding here **/
        binding = FragmentDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.data = detailFragmentData

        // UI related setups.
        with(detailFragmentData){
            // set header image
            binding.gymImageView.load(header_image["desktop"]) {
                crossfade(true)
            }

            binding.ratingView.rating = review_rating.toFloat()

            var detailText = resources.getString(R.string.gym_detail_info, name, category.name, DateTimeHelper.findOpeningTime(first_live_at))
            if(surplus.surplus_allowed){
                detailText += resources.getString(R.string.gym_detail_surplus, name, surplus.formatted_price)
            }
            binding.informationTextView.text = detailText

        }

        Timber.d("DetailFragment created..")
        // Inflate the layout for this fragment
        return binding.root
    }
}