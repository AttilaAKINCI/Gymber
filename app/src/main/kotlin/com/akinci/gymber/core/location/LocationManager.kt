package com.akinci.gymber.core.location

import android.annotation.SuppressLint
import android.content.Context
import com.akinci.gymber.core.location.error.InvalidPermission
import com.akinci.gymber.core.location.error.UnAvailable
import com.akinci.gymber.core.permission.PermissionManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SuppressLint("MissingPermission")
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val permissionManager: PermissionManager,
) {

    private val locationClient by lazy { LocationServices.getFusedLocationProviderClient(context) }

    suspend fun getCurrentLocation(): Result<Coordinate> {
        if (!permissionManager.isLocationPermissionGranted()) return Result.failure(
            InvalidPermission("Required location permission is not provided")
        )

        val priority = if (permissionManager.isFineLocationPermissionGranted()) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }

        return runCatching {
            locationClient.getCurrentLocation(priority, CancellationTokenSource().token).await()
        }.fold(
            onSuccess = {
                Result.success(
                    Coordinate(latitude = it.latitude, longitude = it.longitude)
                )
            },
            onFailure = {
                Timber.e(it)
                Result.failure(UnAvailable("Location request is failed"))
            }
        )
    }
}
