package com.akinci.gymber.common.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

object LocationProvider {


    fun findLastLocation(context: Context){

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(context).lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
            }
    }



    /** Calculate air distance between 2 locations with some Math :) **/
    fun calculateDistanceByKm(
       lat1: Double, lat2: Double, lon1: Double, lon2: Double
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