package com.akinci.gymber.domain

import android.os.Parcelable
import com.akinci.gymber.core.location.Coordinate
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val postCode: String,
    val street: String,
    val number: String,
    val distance: Float? = null,
    val distanceText: String = "",
) : Parcelable {
    fun toCoordinate() = Coordinate(latitude = latitude, longitude = longitude)
}

fun List<Location>.getNearest() = minByOrNull { it.distance ?: Float.MAX_VALUE }
