package com.akinci.gymber.feature.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.akinci.gymber.R
import com.akinci.gymber.common.components.SnackBar
import com.akinci.gymber.common.helper.state.ListState
import com.akinci.gymber.common.helper.state.UIState
import com.akinci.gymber.databinding.FragmentDashboardBinding
import com.akinci.gymber.feature.dashboard.viewmodel.DashboardViewModel
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

        //hide appbar on splash screen
        (activity as AppCompatActivity).supportActionBar?.show()

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
                        SnackBar.make(binding.root, resources.getString(R.string.global_service_error))
                    }
                    else -> { /** NOP **/ }
                }
            }
        }

    }

    private fun getInitialData(){
        // fetch initial breed data
        viewModel.getPartnerList()
    }

}