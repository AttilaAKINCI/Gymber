package com.akinci.gymber.core.utils.distance

import android.location.Location
import com.akinci.gymber.core.location.Coordinate
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  DistanceCalculator is core functionality of distance calculation.
 *  Core logic is separated in order to provide better unit testing capability for [DistanceUtils]
 * */
@Singleton
class DistanceCalculator @Inject constructor() {

    /**
     *  Calculates distance in meters as ellipsoid distance (bird flight distance).
     * **/
    fun calculateFlightDistance(from: Coordinate, to: Coordinate): Float? {
        val results = mutableListOf(0f).toFloatArray()
        Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, results)
        return results.firstOrNull()
    }
}