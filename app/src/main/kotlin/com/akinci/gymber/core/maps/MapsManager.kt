package com.akinci.gymber.core.maps

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 *  MapsManager provides interaction with google maps app/web.
 *  Primary action is opening provided coordinates on google maps app
 *  in case of any malfunction Google maps web will be opened on Web Browser as fallback method
 *
 *  Reference - https://developers.google.com/maps/documentation/urls/get-started
 * **/
class MapsManager @Inject constructor(
    @ApplicationContext val context: Context,
) {
    fun open(
        latitude: Double,
        longitude: Double,
        name: String,
    ): Result<Unit> {
        return runCatching {
            context.startActivity(
                buildAppIntent(latitude = latitude, longitude = longitude, name = name)
            )
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { ex1 ->
                Timber.e(ex1, "Unable to open GoogleMaps App")

                // try to open it using web browser as fallback action
                runCatching {
                    context.startActivity(
                        buildWebIntent(latitude = latitude, longitude = longitude)
                    )
                }.onFailure { ex2 ->
                    Timber.e(ex2, "Unable to open GoogleMaps Web")
                }
            }
        )
    }

    private fun buildAppIntent(latitude: Double, longitude: Double, name: String): Intent {
        val link = "geo:$latitude,$longitude?q=$name".encode()
        return Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            setPackage("com.google.android.apps.maps")
        }
    }

    private fun buildWebIntent(latitude: Double, longitude: Double): Intent {
        val link = "https://www.google.com/maps/search/?api=1&query=$latitude%2C$longitude"
        return Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    private fun String.encode() =
        replace(" ", "%20")
            .replace("|", "%7C")
            .replace(",", "%2C")
}
