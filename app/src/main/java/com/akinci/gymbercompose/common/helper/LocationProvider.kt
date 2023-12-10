package com.akinci.gymbercompose.common.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.akinci.gymbercompose.data.output.Address
import com.akinci.gymbercompose.data.output.Partner
import com.google.android.gms.location.LocationServices

object LocationProvider {

    /** find last Known location and save it in here **/
    var lastKnownLocation: Location? = null
    fun findLastLocation(context: Context) {
        // checks self permission so we need to pass activity context here
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            // Todo be sure this is returning a value. Causes crashes if location couldn't acquired.
            LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { lastKnownLocation = it }
        }
    }

    fun findClosest(addressList: List<Address>) = addressList.minByOrNull { it.distance }
    fun calculateDistances(partnerList: List<Partner>): MutableList<Partner> {
        lastKnownLocation?.let { gpsLocation ->
            partnerList.forEach { partner ->
                partner.locations.forEach { location ->
                    val distance = calculateDistanceByKm(
                        gpsLocation.latitude,
                        gpsLocation.longitude,
                        location.latitude,
                        location.longitude
                    ).toString()
                    location.distance = distance
                }
            }
        }
        return partnerList.toMutableList()
    }

    /** Calculate air distance between 2 locations with some Math :) **/
    private fun calculateDistanceByKm(
        lat1: Double, lon1: Double, lat2: Double, lon2: Double
    ): Int {
        val startLocation = Location("start").apply {
            latitude = lat1
            longitude = lon1
        }

        val destinationLocation = Location("destination").apply {
            latitude = lat2
            longitude = lon2
        }

        return (startLocation.distanceTo(destinationLocation) / 1000).toInt()
    }
}