package com.akinci.gymber.common.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import timber.log.Timber

open class BaseFragment : Fragment() {

    /** Permission Settings **/
    open fun onReturnFromAppPermission(){ /** will be overridden upper layers **/ }

    private val settingsAppPermissionRequest = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        // we are returning our app with back action so
        // we need to check result code with cancel state
        if (result.resultCode == Activity.RESULT_CANCELED) {
            onReturnFromAppPermission()
        }
    }

    fun openAppPermissionSettings(){
        settingsAppPermissionRequest.launch(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", requireActivity().packageName, null)
        })
    }


    /** Location Permission  **/
    open fun onLocationPermissionResponse(granted: Boolean){ /** will be overridden upper layers **/ }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                onLocationPermissionResponse(true)
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                onLocationPermissionResponse(true)
            } else -> {
                // No location access granted.
                onLocationPermissionResponse(false)
            }
        }
    }

    fun checkLocationPermission(alreadyGranted: (()->Unit)) {
        Timber.d("Location permission check")
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }else{
            Timber.d("Location permission already granted")
            alreadyGranted.invoke()
        }
    }
}