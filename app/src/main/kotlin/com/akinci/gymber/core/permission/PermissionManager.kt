package com.akinci.gymber.core.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(
    @ApplicationContext val context: Context,
) {
    companion object {
        const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    }

    private fun isGranted(permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    /**
     *  Expose permission checks via functions.
     * **/
    fun isLocationPermissionGranted() = isGranted(FINE_LOCATION) || isGranted(COARSE_LOCATION)
    fun isFineLocationPermissionGranted() = isGranted(FINE_LOCATION)
}
