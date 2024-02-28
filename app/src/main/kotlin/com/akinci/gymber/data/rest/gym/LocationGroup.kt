package com.akinci.gymber.data.rest.gym

import kotlinx.serialization.Serializable

@Serializable
data class LocationGroup(
    val latitude: Double,
    val longitude: Double,
    val locations: List<Location>
)
