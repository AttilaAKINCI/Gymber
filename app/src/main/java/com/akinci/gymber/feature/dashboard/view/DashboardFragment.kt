package com.akinci.gymber.feature.dashboard.view

import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.akinci.gymber.R
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
class DashboardFragment : Fragment() {

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
            swipeGymLeft()
        }

        binding.selectButton.setOnClickListener {
            Timber.d("selectButton clicked..")
            checkGymDetails()
        }

        binding.likeButton.setOnClickListener {
            Timber.d("likeButton clicked..")
            swipeGymRight()
        }

        binding.flingContainer.setOnItemClickListener { _, _ ->
            Timber.d("Gym card selected... Navigate to Detail page")
            checkGymDetails()
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

    private fun checkGymDetails(){
        /** Navigate user to gym detail page. **/
        Timber.d("Navigated to  DetailFragment..")
        NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_detailFragment)
    }

    private fun swipeGymRight(){
        // programmatically swipe right
        binding.flingContainer.topCardListener.selectRight()
    }

    private fun swipeGymLeft(){
        // programmatically swipe left
        binding.flingContainer.topCardListener.selectLeft()
    }

    private fun getInitialData(){
        // fetch initial partner data
        viewModel.getPartnerList()
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