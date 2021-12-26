package com.akinci.gymber.common.helper

import android.location.Location

object DistanceCalculator {

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