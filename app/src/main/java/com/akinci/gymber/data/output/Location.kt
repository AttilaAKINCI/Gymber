package com.akinci.gymber.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val id: Int,
    val street_name: String?,
    val house_number: String?,
    val addition: String?,
    val city: String,
    val zip_code: String?,
    val latitude: Double,
    val longitude: Double
)
