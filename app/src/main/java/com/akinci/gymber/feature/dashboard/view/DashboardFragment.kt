package com.akinci.gymber.feature.dashboard.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Shader
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import coil.load
import coil.transform.RoundedCornersTransformation
import com.akinci.gymber.MainActivity
import com.akinci.gymber.R
import com.akinci.gymber.common.base.BaseFragment
import com.akinci.gymber.common.components.DialogProvider
import com.akinci.gymber.common.components.SnackBar
import com.akinci.gymber.common.components.TileDrawable
import com.akinci.gymber.common.helper.state.ListState
import com.akinci.gymber.common.helper.state.UIState
import com.akinci.gymber.common.network.NetworkState
import com.akinci.gymber.databinding.FragmentDashboardBinding
import com.akinci.gymber.feature.dashboard.adapter.GymCardAdapter
import com.akinci.gymber.feature.dashboard.viewmodel.DashboardViewModel
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    lateinit var binding: FragmentDashboardBinding
    private val viewModel : DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /** Initialization of ViewBinding, not need for DataBinding here **/
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        // shared element transition
        handleSharedElementTransitionAnimation()

        // set tile background
        val backgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.pattern)
        binding.tileImageView.setImageDrawable(TileDrawable(backgroundDrawable!!, Shader.TileMode.REPEAT))

        // start screen with shimmer animation
        binding.shimmerLayout.startShimmer()

        // start gymber icon animation
        binding.animation.playAnimation()

        binding.dislikeButton.setOnClickListener {
            Timber.d("dislikeButton clicked..")
            checkLocationPermission {
                // permission already granted.
                swipeGymLeft()
            }
        }

        binding.selectButton.setOnClickListener {
            Timber.d("selectButton clicked..")
            checkLocationPermission {
                // permission already granted.
                checkGymDetails()
            }
        }

        binding.likeButton.setOnClickListener {
            Timber.d("likeButton clicked..")
            checkLocationPermission {
                // permission already granted.
                swipeGymRight()
            }
        }

        binding.flingContainer.setOnItemClickListener { _, _ ->
            Timber.d("Gym card selected... Navigate to Detail page")
            checkLocationPermission {
                // permission already granted.
                checkGymDetails()
            }
        }

        binding.flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener{
            override fun removeFirstObjectInAdapter() {
               viewModel.removeItem()
            }

            override fun onLeftCardExit(p0: Any?) {
                Timber.d("Gym swiped to left..")
            }

            override fun onRightCardExit(p0: Any?) {
                Timber.d("Gym swiped to right..")

                viewModel.getLastSwipedItem()?.let {
                    // start Match animation
                    if(it.isAMatch){
                        /** setup visuals **/
                        binding.matchSubTitleTextView.text = resources.getString(R.string.match_sub_title, it.name)
                        binding.matchGymImageView.load(it.header_image["desktop"]) {
                            crossfade(true)
                            transformations(RoundedCornersTransformation(25f))
                        }

                        binding.matchCallButton.setOnClickListener{
                            SnackBar.make(binding.matchLayout, resources.getString(R.string.match_button_text_call_warning)).show()
                        }

                        binding.matchMessageButton.setOnClickListener{
                            SnackBar.make(binding.matchLayout, resources.getString(R.string.match_button_text_message_warning)).show()
                        }

                        binding.matchSkipButton.setOnClickListener{
                            binding.confettiAnimation.pauseAnimation()
                            matchAnimation(false)
                        }

                        binding.matchLayout.visibility = View.VISIBLE
                        binding.confettiAnimation.playAnimation()
                        matchAnimation(true)
                    }
                }
            }

            override fun onAdapterAboutToEmpty(p0: Int) {
                // load data if you have more
                // for our case data is fetched totally in one request
            }

            override fun onScroll(p0: Float) {
                Timber.d("Scroll : $p0")
                (binding.flingContainer.adapter as GymCardAdapter).flingAngleProvider?.invoke(p0)
            }
        })

        Timber.d("DashboardFragment created..")
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInitialData()

        // observe partner list data
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.partnerListData.collect{ state ->
                when(state){
                    is ListState.OnLoading -> { }
                    is ListState.OnData -> {
                        state.data?.let {
                            /**
                             * There is a bug on ArrayAdapter usage with SwipeFlingAdapterView.
                             * Adapter can not be set with empty list. Thus, I set Adapter when data arrives
                             * **/
                            if(binding.flingContainer.adapter == null){
                                GymCardAdapter(
                                    requireContext(),
                                    it.toMutableList(),
                                    onDataLoaded = {
                                        if(binding.shimmerContainer.isVisible){
                                            binding.shimmerLayout.stopShimmer()
                                            binding.shimmerContainer.visibility = View.GONE
                                        }
                                        binding.flingContainer.alpha = 1f
                                    }
                                ).also { adapter ->
                                    binding.flingContainer.adapter = adapter
                                }.notifyDataSetChanged()
                            }else{
                                // this block only used for
                                (binding.flingContainer.adapter as GymCardAdapter).also { adapter ->
                                    adapter.items = it.toMutableList()
                                }.notifyDataSetChanged()
                            }
                        }?: run{
                            // for null response...
                        }
                    }
                    else -> { /** NOP **/ }
                }
            }
        }

        // observe ui events
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect{ state ->
                when(state){
                    is UIState.OnServiceError -> {
                         SnackBar.make(binding.root, resources.getString(R.string.global_service_error)).show()
                    }
                    is UIState.OnNetworkError -> {
                        DialogProvider.createNetworkProblemAlertDialog(
                            requireContext(),
                            positiveAction = { dialog ->
                                if(viewModel.networkChecker.networkState.value == NetworkState.Connected){
                                    getInitialData()
                                    dialog.dismiss()
                                }else{
                                    SnackBar.make(binding.root, resources.getText(R.string.network_still_unavailable)).show()
                                }
                            }
                        )
                    }
                    else -> { /** NOP **/ }
                }
            }
        }
    }

    override fun onLocationPermissionResponse(granted: Boolean) {
        if(granted){
            getInitialData()
        }else{
            // for negative response show location permission dialog.
            DialogProvider.createLocationPermissionAlertDialog(
                requireContext(),
                positiveAction = {
                    // open app permission settings
                    openAppPermissionSettings()
                }
            )
        }
    }

    override fun onReturnFromAppPermission() {
        getInitialData()
    }

    private fun getInitialData(){
        checkLocationPermission {
            // fetch initial partner data
            viewModel.getPartnerList()
        }
    }

    private fun checkGymDetails(){
        /** Navigate user to gym detail page. **/
        Timber.d("Navigated to  DetailFragment..")
        NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_detailFragment)
    }

    private fun swipeGymRight(){
        // programmatically swipe right
        // automatically triggers OnFlingListener.onRightCardExit
        checkLocationPermission {
            binding.flingContainer?.topCardListener?.selectRight()
        }
    }

    private fun swipeGymLeft(){
        // programmatically swipe left
        // automatically triggers OnFlingListener.onLeftCardExit
        checkLocationPermission {
            binding.flingContainer?.topCardListener?.selectLeft()
        }
    }

    // Match Animation
    private fun matchAnimation(show: Boolean){
        val alpha = if(show) { 1f } else { 0f }
        val sDelay = if(show) { 500L } else { 0L }
        ObjectAnimator.ofFloat(binding.matchLayout, "alpha", alpha).apply {
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = sDelay
            duration = 500
            doOnEnd {
                if(!show){ binding.matchLayout.visibility = View.GONE }
            }
        }.start()
    }

    private fun handleSharedElementTransitionAnimation(){
        /** view transition configuration **/
        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
        enterTransitionSet.duration = 1000
        sharedElementEnterTransition = enterTransitionSet
        /** **/
    }

}