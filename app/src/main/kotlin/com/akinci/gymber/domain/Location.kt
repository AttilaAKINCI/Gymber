package com.akinci.gymber.domain

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
) : Parcelable
