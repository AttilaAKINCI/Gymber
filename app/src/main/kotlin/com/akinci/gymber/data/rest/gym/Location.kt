package com.akinci.gymber.data.rest.gym

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Int,
    @SerialName("partner_id")
    val gymId: Int,
    @SerialName("street_name")
    val streetName: String,
    @SerialName("house_number")
    val houseNumber: String,
    val addition: String,
    @SerialName("zip_code")
    val zipCode: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
)
