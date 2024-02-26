package com.akinci.gymber.domain.data

import android.os.Parcelable
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
) : Parcelable
