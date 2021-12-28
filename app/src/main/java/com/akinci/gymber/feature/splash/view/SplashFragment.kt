package com.akinci.gymber.feature.splash.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.akinci.gymber.R
import com.akinci.gymber.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private val animationTime = 2000L
  //  private val animationTime = 15000L  // TODO update time later

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        /** Initialization of ViewBinding not need for DataBinding here **/
        binding = FragmentSplashBinding.inflate(layoutInflater)

        //hide appbar on splash screen
        (activity as AppCompatActivity).supportActionBar?.hide()

        Timber.d("SplashFragment created..")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // when observe anything, start animation.
        binding.animation.playAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            contentAlphaAnimation()
        }, animationTime)
    }

    private fun contentAlphaAnimation(){
        // TODO fill here later.
        // on animation end
        navigateToDashboard()
    }

    private fun navigateToDashboard(){
        /** Navigate to Dashboard Page **/
        val imageTransition = resources.getString(R.string.image_transition)
        val extras = FragmentNavigatorExtras(binding.animation to imageTransition)

        Timber.d("Navigated to  DashboardFragment..")

        /** Navigate to Dashboard Page **/
        NavHostFragment.findNavController(this).navigate(
            R.id.action_splashFragment_to_dashboardFragment,
            null,
            null,
            extras
        )
    }

}