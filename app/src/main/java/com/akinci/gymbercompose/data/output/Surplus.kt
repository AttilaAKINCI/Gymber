package com.akinci.gymbercompose.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Surplus(
    val surplus_allowed: Boolean,
    val formatted_price: String
)
