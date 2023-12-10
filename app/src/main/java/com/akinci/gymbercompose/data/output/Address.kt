package com.akinci.gymbercompose.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    val id: Int,
    val street_name: String?,
    val house_number: String?,
    val addition: String?,
    val city: String,
    val zip_code: String?,
    val latitude: Double,
    val longitude: Double,
    var distance: String = "" // this parameter holds distance between gps location and target. it should be provided by backend
)
