package com.akinci.gymber.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LocationGroup(
    val latitude: Double,
    val longitude: Double,
    val locations: List<Location>
)
