package com.akinci.gymber.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val desktop: String,
    val xxxhdpi: String,
    val xxhdpi: String,
    val tablet: String,
    val mobile: String,
    val xhdpi: String,
    val hdpi: String,
    val mdpi: String,
    val ldpi: String,
    val thumbnail: String,
    val orig: String,
)
